// File: src/com/medicalappointment/dao/AppointmentDAO.java
package com.medicalappointment.dao;

import com.medicalappointment.model.Appointment;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public interface AppointmentDAO {
    int bookAppointment(Appointment appointment) throws SQLException;
    boolean isSlotTaken(int doctorId, Date date, Time startTime) throws SQLException;
    Appointment findById(int appointmentId) throws SQLException;
    List<Appointment> findByPatient(int patientId) throws SQLException;
    List<Appointment> findByDoctor(int doctorId) throws SQLException;
    List<Appointment> findByDoctorAndDate(int doctorId, Date date) throws SQLException;
    List<Appointment> findAll() throws SQLException;
    List<Appointment> filterAppointments(Integer doctorId, Integer patientId, String status,
                                          Date fromDate, Date toDate) throws SQLException;
    boolean updateStatus(int appointmentId, String status, String rejectionReason,
                          String cancellationReason) throws SQLException;
    int countByStatus(String status) throws SQLException;
    int countAllAppointments() throws SQLException;
    int countTodayForDoctor(int doctorId) throws SQLException;
    int countUpcomingForPatient(int patientId) throws SQLException;
}