// File: src/com/medicalappointment/service/AppointmentService.java
package com.medicalappointment.service;

import com.medicalappointment.exception.BookingConflictException;
import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.Appointment;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Map;

public interface AppointmentService {

    int bookAppointment(int patientId, int doctorId, Date date, Time startTime, String reason)
            throws ValidationException, BookingConflictException, SQLException, ResourceNotFoundException;

    void confirmAppointment(int appointmentId, int doctorId) throws SQLException, ResourceNotFoundException;
    void rejectAppointment(int appointmentId, int doctorId, String reason)
            throws SQLException, ResourceNotFoundException, ValidationException;
    void completeAppointment(int appointmentId, int doctorId)
            throws SQLException, ResourceNotFoundException, ValidationException;
    void cancelAppointment(int appointmentId, String reason)
            throws SQLException, ResourceNotFoundException, ValidationException;

    int rescheduleAppointment(int appointmentId, Date newDate, Time newStartTime)
            throws ValidationException, BookingConflictException, SQLException, ResourceNotFoundException;

    Appointment getById(int appointmentId) throws SQLException, ResourceNotFoundException;
    List<Appointment> getForPatient(int patientId) throws SQLException;
    List<Appointment> getForDoctor(int doctorId) throws SQLException;
    List<Appointment> getForDoctorAndDate(int doctorId, Date date) throws SQLException;
    List<Appointment> getAll() throws SQLException;
    List<Appointment> filter(Integer doctorId, Integer patientId, String status, Date from, Date to)
            throws SQLException;

    int countTodayForDoctor(int doctorId) throws SQLException;
    int countUpcomingForPatient(int patientId) throws SQLException;
    Map<String, Integer> getStatusCounts() throws SQLException;
    int countAll() throws SQLException;
}