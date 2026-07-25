// File: src/com/medicalappointment/service/PatientService.java
package com.medicalappointment.service;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.model.Patient;

import java.sql.SQLException;
import java.util.List;

public interface PatientService {
    Patient getByUserId(int userId) throws SQLException, ResourceNotFoundException;
    Patient getById(int patientId) throws SQLException, ResourceNotFoundException;
    List<Patient> getAll() throws SQLException;
    void updateProfile(Patient patient) throws SQLException;
    int countTotalPatients() throws SQLException;
}