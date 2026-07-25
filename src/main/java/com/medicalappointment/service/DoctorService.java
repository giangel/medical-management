// File: src/com/medicalappointment/service/DoctorService.java
package com.medicalappointment.service;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.Doctor;
import com.medicalappointment.model.DoctorAvailability;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public interface DoctorService {
    Doctor getById(int doctorId) throws SQLException, ResourceNotFoundException;
    Doctor getByUserId(int userId) throws SQLException, ResourceNotFoundException;
    List<Doctor> getAll() throws SQLException;
    List<Doctor> search(String keyword, Integer departmentId, Integer specialtyId) throws SQLException;

    int registerDoctor(String firstName, String lastName, String email, String phoneNumber,
                        String temporaryPassword, Integer departmentId, Integer specialtyId,
                        String licenseNumber) throws ValidationException, SQLException;

    void updateProfile(Doctor doctor) throws SQLException;
    void setAcceptingAppointments(int doctorId, boolean accepting) throws SQLException;
    int countTotalDoctors() throws SQLException;

    void setRecurringAvailability(int doctorId, int dayOfWeek, Time startTime, Time endTime,
                                   int slotMinutes) throws ValidationException, SQLException;
    void setDateOverride(int doctorId, Date date, boolean unavailable, Time startTime, Time endTime)
            throws SQLException;
    List<DoctorAvailability> getRecurringAvailability(int doctorId) throws SQLException;
    List<DoctorAvailability> getOverrides(int doctorId) throws SQLException;

    /**
     * Computes the bookable start times for a doctor on a given date by combining
     * recurring weekly availability, any date-specific override, the doctor's slot
     * duration, and already booked appointments for that date.
     */
    List<Time> getAvailableSlots(int doctorId, Date date) throws SQLException;
}