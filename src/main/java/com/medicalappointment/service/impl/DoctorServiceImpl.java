// File: src/com/medicalappointment/service/impl/DoctorServiceImpl.java
package com.medicalappointment.service.impl;

import com.medicalappointment.dao.DoctorAvailabilityDAO;
import com.medicalappointment.dao.DoctorDAO;
import com.medicalappointment.dao.UserDAO;
import com.medicalappointment.dao.AppointmentDAO;
import com.medicalappointment.dao.impl.AppointmentDAOImpl;
import com.medicalappointment.dao.impl.DoctorAvailabilityDAOImpl;
import com.medicalappointment.dao.impl.DoctorDAOImpl;
import com.medicalappointment.dao.impl.UserDAOImpl;
import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.*;
import com.medicalappointment.service.DoctorService;
import com.medicalappointment.util.PasswordUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DoctorServiceImpl implements DoctorService {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final int DOCTOR_ROLE_ID = 2; // matches the ordering inserted in the schema seed data

    private final DoctorDAO doctorDAO = new DoctorDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final DoctorAvailabilityDAO availabilityDAO = new DoctorAvailabilityDAOImpl();
    private final AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    @Override
    public Doctor getById(int doctorId) throws SQLException, ResourceNotFoundException {
        Doctor doctor = doctorDAO.findById(doctorId);
        if (doctor == null) {
            throw new ResourceNotFoundException("Doctor not found.");
        }
        return doctor;
    }

    @Override
    public Doctor getByUserId(int userId) throws SQLException, ResourceNotFoundException {
        Doctor doctor = doctorDAO.findByUserId(userId);
        if (doctor == null) {
            throw new ResourceNotFoundException("Doctor profile not found.");
        }
        return doctor;
    }

    @Override
    public List<Doctor> getAll() throws SQLException {
        return doctorDAO.findAll();
    }

    @Override
    public List<Doctor> search(String keyword, Integer departmentId, Integer specialtyId) throws SQLException {
        return doctorDAO.search(keyword, departmentId, specialtyId);
    }

    @Override
    public int registerDoctor(String firstName, String lastName, String email, String phoneNumber,
                               String temporaryPassword, Integer departmentId, Integer specialtyId,
                               String licenseNumber) throws ValidationException, SQLException {

        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
            throw new ValidationException("First and last name are required.");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ValidationException("Please provide a valid email address.");
        }
        if (userDAO.emailExists(email.trim())) {
            throw new ValidationException("An account with this email already exists.");
        }
        if (!PasswordUtil.isStrongEnough(temporaryPassword)) {
            throw new ValidationException("Temporary password must be at least 8 characters long.");
        }

        User user = new User();
        user.setRoleId(DOCTOR_ROLE_ID);
        user.setFirstName(firstName.trim());
        user.setLastName(lastName.trim());
        user.setEmail(email.trim());
        user.setPhoneNumber(phoneNumber);
        user.setPasswordHash(PasswordUtil.hash(temporaryPassword));
        user.setActive(true);
        int userId = userDAO.createUser(user);

        Doctor doctor = new Doctor();
        doctor.setUserId(userId);
        doctor.setDepartmentId(departmentId);
        doctor.setSpecialtyId(specialtyId);
        doctor.setLicenseNumber(licenseNumber);
        doctor.setDefaultSlotMinutes(30);
        doctor.setAcceptingAppointments(true);
        return doctorDAO.createDoctor(doctor);
    }

    @Override
    public void updateProfile(Doctor doctor) throws SQLException {
        doctorDAO.updateDoctor(doctor);
    }

    @Override
    public void setAcceptingAppointments(int doctorId, boolean accepting) throws SQLException {
        doctorDAO.setAcceptingAppointments(doctorId, accepting);
    }

    @Override
    public int countTotalDoctors() throws SQLException {
        return doctorDAO.countAllDoctors();
    }

    @Override
    public void setRecurringAvailability(int doctorId, int dayOfWeek, Time startTime, Time endTime,
                                          int slotMinutes) throws ValidationException, SQLException {
        if (dayOfWeek < 0 || dayOfWeek > 6) {
            throw new ValidationException("Day of week must be between 0 and 6.");
        }
        if (!endTime.after(startTime)) {
            throw new ValidationException("End time must be after start time.");
        }
        DoctorAvailability availability = new DoctorAvailability();
        availability.setDoctorId(doctorId);
        availability.setRecordType(AvailabilityRecordType.RECURRING);
        availability.setDayOfWeek(dayOfWeek);
        availability.setStartTime(startTime);
        availability.setEndTime(endTime);
        availability.setSlotMinutes(slotMinutes);
        availability.setUnavailable(false);
        availabilityDAO.createAvailability(availability);
    }

    @Override
    public void setDateOverride(int doctorId, Date date, boolean unavailable, Time startTime, Time endTime)
            throws SQLException {
        DoctorAvailability availability = new DoctorAvailability();
        availability.setDoctorId(doctorId);
        availability.setRecordType(AvailabilityRecordType.DATE_OVERRIDE);
        availability.setSpecificDate(date);
        availability.setUnavailable(unavailable);
        availability.setStartTime(startTime);
        availability.setEndTime(endTime);
        availability.setSlotMinutes(30);
        availabilityDAO.createAvailability(availability);
    }

    @Override
    public List<DoctorAvailability> getRecurringAvailability(int doctorId) throws SQLException {
        return availabilityDAO.findRecurringByDoctor(doctorId);
    }

    @Override
    public List<DoctorAvailability> getOverrides(int doctorId) throws SQLException {
        return availabilityDAO.findOverridesByDoctor(doctorId);
    }

    @Override
    public List<Time> getAvailableSlots(int doctorId, Date date) throws SQLException {
        List<Time> slots = new ArrayList<>();

        DoctorAvailability override = availabilityDAO.findOverrideForDate(doctorId, date);
        if (override != null && override.isUnavailable()) {
            return slots;
        }

        List<LocalTime[]> windows = new ArrayList<>();
        int slotMinutes;

        if (override != null && override.getStartTime() != null && override.getEndTime() != null) {
            windows.add(new LocalTime[]{override.getStartTime().toLocalTime(), override.getEndTime().toLocalTime()});
            slotMinutes = override.getSlotMinutes();
        } else {
            LocalDate localDate = date.toLocalDate();
            int dayOfWeek = localDate.getDayOfWeek().getValue() % 7; 
            List<DoctorAvailability> recurring = availabilityDAO.findRecurringByDoctor(doctorId);
            slotMinutes = 30;
            for (DoctorAvailability rule : recurring) {
                if (rule.getDayOfWeek() != null && rule.getDayOfWeek() == dayOfWeek
                        && rule.getStartTime() != null && rule.getEndTime() != null) {
                    windows.add(new LocalTime[]{rule.getStartTime().toLocalTime(), rule.getEndTime().toLocalTime()});
                    slotMinutes = rule.getSlotMinutes();
                }
            }
        }

        if (windows.isEmpty()) {
            return slots;
        }

        List<Appointment> bookedOnDate = appointmentDAO.findByDoctorAndDate(doctorId, date);
        List<Time> takenStartTimes = new ArrayList<>();
        for (Appointment appointment : bookedOnDate) {
            if (appointment.getStatus() == AppointmentStatus.PENDING
                    || appointment.getStatus() == AppointmentStatus.CONFIRMED
                    || appointment.getStatus() == AppointmentStatus.RESCHEDULED) {
                takenStartTimes.add(appointment.getStartTime());
            }
        }

        boolean isToday = date.toLocalDate().isEqual(LocalDate.now());
        LocalTime now = LocalTime.now();

        for (LocalTime[] window : windows) {
            LocalTime cursor = window[0];
            while (cursor.plusMinutes(slotMinutes).compareTo(window[1]) <= 0) {
                if (!isToday || cursor.isAfter(now)) {
                    Time candidate = Time.valueOf(cursor);
                    
                    boolean taken = false;
                    for (Time t : takenStartTimes) {
                        if (t.toLocalTime().equals(cursor)) {
                            taken = true;
                            break;
                        }
                    }

                    if (!taken) {
                        slots.add(candidate);
                    }
                }
                cursor = cursor.plusMinutes(slotMinutes);
            }
        }
        return slots;
    }
}