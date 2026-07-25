// File: src/com/medicalappointment/dao/DoctorDAO.java
package com.medicalappointment.dao;

import com.medicalappointment.model.Doctor;
import java.sql.SQLException;
import java.util.List;

public interface DoctorDAO {
    int createDoctor(Doctor doctor) throws SQLException;
    Doctor findById(int doctorId) throws SQLException;
    Doctor findByUserId(int userId) throws SQLException;
    boolean updateDoctor(Doctor doctor) throws SQLException;
    boolean setAcceptingAppointments(int doctorId, boolean accepting) throws SQLException;
    List<Doctor> findAll() throws SQLException;
    List<Doctor> search(String keyword, Integer departmentId, Integer specialtyId) throws SQLException;
    int countAllDoctors() throws SQLException;
}