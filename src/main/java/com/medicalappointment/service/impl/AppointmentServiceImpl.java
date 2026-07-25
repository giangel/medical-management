// File: src/com/medicalappointment/service/impl/AppointmentServiceImpl.java
package com.medicalappointment.service.impl;

import com.medicalappointment.dao.AppointmentDAO;
import com.medicalappointment.dao.DoctorDAO;
import com.medicalappointment.dao.PatientDAO;
import com.medicalappointment.dao.impl.AppointmentDAOImpl;
import com.medicalappointment.dao.impl.DoctorDAOImpl;
import com.medicalappointment.dao.impl.PatientDAOImpl;
import com.medicalappointment.exception.BookingConflictException;
import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.*;
import com.medicalappointment.service.AppointmentService;
import com.medicalappointment.service.DoctorService;
import com.medicalappointment.service.NotificationService;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private final DoctorDAO doctorDAO = new DoctorDAOImpl();
    private final PatientDAO patientDAO = new PatientDAOImpl();
    private final DoctorService doctorService = new DoctorServiceImpl();
    private final NotificationService notificationService = new NotificationServiceImpl();

    private static final int DEFAULT_SLOT_MINUTES_FALLBACK = 30;

    @Override
    public int bookAppointment(int patientId, int doctorId, Date date, Time startTime, String reason)
            throws ValidationException, BookingConflictException, SQLException, ResourceNotFoundException {

        if (reason == null || reason.isBlank()) {
            throw new ValidationException("Please provide a reason for the appointment.");
        }
        if (date == null || date.toLocalDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Appointment date cannot be in the past.");
        }

        Doctor doctor = doctorDAO.findById(doctorId);
        if (doctor == null) {
            throw new ResourceNotFoundException("Doctor not found.");
        }
        if (!doctor.isAcceptingAppointments()) {
            throw new ValidationException("This doctor is not currently accepting appointments.");
        }
        if (patientDAO.findById(patientId) == null) {
            throw new ResourceNotFoundException("Patient not found.");
        }

        int slotMinutes = doctor.getDefaultSlotMinutes() > 0 ? doctor.getDefaultSlotMinutes() : DEFAULT_SLOT_MINUTES_FALLBACK;

        List<Time> availableSlots = doctorService.getAvailableSlots(doctorId, date);
        boolean matchesAvailableSlot = availableSlots.stream().anyMatch(t -> t.equals(startTime));
        if (!matchesAvailableSlot) {
            throw new BookingConflictException(
                "The selected time is no longer available. Please choose another slot.");
        }

        Time endTime = Time.valueOf(startTime.toLocalTime().plusMinutes(slotMinutes));

        Appointment appointment = new Appointment();
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentDate(date);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setReason(reason.trim());
        appointment.setStatus(AppointmentStatus.PENDING);

        int appointmentId;
        try {
            appointmentId = appointmentDAO.bookAppointment(appointment);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new BookingConflictException(
                    "This appointment slot has just been booked by another patient. Please choose a different time.");
            }
            throw e;
        }

        notificationService.notifyUser(doctor.getUserId(), appointmentId, "New appointment request",
            "You have a new appointment request for " + date + " at " + startTime + ".",
            NotificationType.BOOKING);

        return appointmentId;
    }

    @Override
    public void confirmAppointment(int appointmentId, int doctorId) throws SQLException, ResourceNotFoundException {
        Appointment appointment = requireAppointmentForDoctor(appointmentId, doctorId);
        appointmentDAO.updateStatus(appointmentId, AppointmentStatus.CONFIRMED.name(), null, null);
        notifyPatient(appointment, "Appointment confirmed",
            "Your appointment on " + appointment.getAppointmentDate() + " at " + appointment.getStartTime()
                + " has been confirmed.", NotificationType.CONFIRMATION);
    }

    @Override
    public void rejectAppointment(int appointmentId, int doctorId, String reason)
            throws SQLException, ResourceNotFoundException, ValidationException {
        if (reason == null || reason.isBlank()) {
            throw new ValidationException("Please provide a reason for rejecting this appointment.");
        }
        Appointment appointment = requireAppointmentForDoctor(appointmentId, doctorId);
        appointmentDAO.updateStatus(appointmentId, AppointmentStatus.REJECTED.name(), reason.trim(), null);
        notifyPatient(appointment, "Appointment rejected",
            "Your appointment request on " + appointment.getAppointmentDate() + " at "
                + appointment.getStartTime() + " was rejected. Reason: " + reason.trim(),
            NotificationType.REJECTION);
    }

    @Override
    public void completeAppointment(int appointmentId, int doctorId)
            throws SQLException, ResourceNotFoundException, ValidationException {
        Appointment appointment = requireAppointmentForDoctor(appointmentId, doctorId);
        if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new ValidationException("Only confirmed appointments can be marked as completed.");
        }
        appointmentDAO.updateStatus(appointmentId, AppointmentStatus.COMPLETED.name(), null, null);
        notifyPatient(appointment, "Appointment completed",
            "Your appointment on " + appointment.getAppointmentDate() + " has been marked as completed.",
            NotificationType.COMPLETION);
    }

    @Override
    public void cancelAppointment(int appointmentId, String reason)
            throws SQLException, ResourceNotFoundException, ValidationException {
        Appointment appointment = appointmentDAO.findById(appointmentId);
        if (appointment == null) {
            throw new ResourceNotFoundException("Appointment not found.");
        }
        if (appointment.getStatus() != AppointmentStatus.PENDING
                && appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new ValidationException("This appointment can no longer be cancelled.");
        }
        appointmentDAO.updateStatus(appointmentId, AppointmentStatus.CANCELLED.name(), null,
            reason != null ? reason.trim() : null);

        Doctor doctor = doctorDAO.findById(appointment.getDoctorId());
        if (doctor != null) {
            notificationService.notifyUser(doctor.getUserId(), appointmentId, "Appointment cancelled",
                "The appointment on " + appointment.getAppointmentDate() + " at " + appointment.getStartTime()
                    + " was cancelled by the patient.", NotificationType.CANCELLATION);
        }
    }

    @Override
    public int rescheduleAppointment(int appointmentId, Date newDate, Time newStartTime)
            throws ValidationException, BookingConflictException, SQLException, ResourceNotFoundException {

        Appointment original = appointmentDAO.findById(appointmentId);
        if (original == null) {
            throw new ResourceNotFoundException("Appointment not found.");
        }
        if (original.getStatus() != AppointmentStatus.PENDING && original.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new ValidationException("This appointment can no longer be rescheduled.");
        }

        int newAppointmentId = bookAppointment(original.getPatientId(), original.getDoctorId(),
            newDate, newStartTime, original.getReason());

        appointmentDAO.updateStatus(appointmentId, AppointmentStatus.RESCHEDULED.name(), null, null);

        Doctor doctor = doctorDAO.findById(original.getDoctorId());
        if (doctor != null) {
            notificationService.notifyUser(doctor.getUserId(), newAppointmentId, "Appointment rescheduled",
                "An appointment was rescheduled from " + original.getAppointmentDate() + " to " + newDate + ".",
                NotificationType.RESCHEDULE);
        }

        return newAppointmentId;
    }

    @Override
    public Appointment getById(int appointmentId) throws SQLException, ResourceNotFoundException {
        Appointment appointment = appointmentDAO.findById(appointmentId);
        if (appointment == null) {
            throw new ResourceNotFoundException("Appointment not found.");
        }
        return appointment;
    }

    @Override
    public List<Appointment> getForPatient(int patientId) throws SQLException {
        return appointmentDAO.findByPatient(patientId);
    }

    @Override
    public List<Appointment> getForDoctor(int doctorId) throws SQLException {
        return appointmentDAO.findByDoctor(doctorId);
    }

    @Override
    public List<Appointment> getForDoctorAndDate(int doctorId, Date date) throws SQLException {
        return appointmentDAO.findByDoctorAndDate(doctorId, date);
    }

    @Override
    public List<Appointment> getAll() throws SQLException {
        return appointmentDAO.findAll();
    }

    @Override
    public List<Appointment> filter(Integer doctorId, Integer patientId, String status, Date from, Date to)
            throws SQLException {
        return appointmentDAO.filterAppointments(doctorId, patientId, status, from, to);
    }

    @Override
    public int countTodayForDoctor(int doctorId) throws SQLException {
        return appointmentDAO.countTodayForDoctor(doctorId);
    }

    @Override
    public int countUpcomingForPatient(int patientId) throws SQLException {
        return appointmentDAO.countUpcomingForPatient(patientId);
    }

    @Override
    public Map<String, Integer> getStatusCounts() throws SQLException {
        Map<String, Integer> counts = new HashMap<>();
        for (AppointmentStatus status : AppointmentStatus.values()) {
            counts.put(status.name(), appointmentDAO.countByStatus(status.name()));
        }
        return counts;
    }

    @Override
    public int countAll() throws SQLException {
        return appointmentDAO.countAllAppointments();
    }

    private Appointment requireAppointmentForDoctor(int appointmentId, int doctorId)
            throws SQLException, ResourceNotFoundException {
        Appointment appointment = appointmentDAO.findById(appointmentId);
        if (appointment == null || appointment.getDoctorId() != doctorId) {
            throw new ResourceNotFoundException("Appointment not found for this doctor.");
        }
        return appointment;
    }

    private void notifyPatient(Appointment appointment, String title, String message, NotificationType type)
            throws SQLException {
        Patient patient = patientDAO.findById(appointment.getPatientId());
        if (patient != null) {
            notificationService.notifyUser(patient.getUserId(), appointment.getAppointmentId(), title, message, type);
        }
    }
}