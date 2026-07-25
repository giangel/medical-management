package com.medicalappointment.dao;

import com.medicalappointment.model.Patient;
import java.sql.SQLException;
import java.util.List;

public interface PatientDAO {
    int createPatient(Patient patient) throws SQLException;
    Patient findById(int patientId) throws SQLException;
    Patient findByUserId(int userId) throws SQLException;
    boolean updatePatient(Patient patient) throws SQLException;
    List<Patient> findAll() throws SQLException;
    int countAllPatients() throws SQLException;
}