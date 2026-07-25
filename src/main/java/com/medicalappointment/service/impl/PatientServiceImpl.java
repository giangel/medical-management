// File: src/com/medicalappointment/service/impl/PatientServiceImpl.java
package com.medicalappointment.service.impl;

import com.medicalappointment.dao.PatientDAO;
import com.medicalappointment.dao.impl.PatientDAOImpl;
import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.model.Patient;
import com.medicalappointment.service.PatientService;

import java.sql.SQLException;
import java.util.List;

public class PatientServiceImpl implements PatientService {

    private final PatientDAO patientDAO = new PatientDAOImpl();

    @Override
    public Patient getByUserId(int userId) throws SQLException, ResourceNotFoundException {
        Patient patient = patientDAO.findByUserId(userId);
        if (patient == null) {
            throw new ResourceNotFoundException("Patient profile not found.");
        }
        return patient;
    }

    @Override
    public Patient getById(int patientId) throws SQLException, ResourceNotFoundException {
        Patient patient = patientDAO.findById(patientId);
        if (patient == null) {
            throw new ResourceNotFoundException("Patient not found.");
        }
        return patient;
    }

    @Override
    public List<Patient> getAll() throws SQLException {
        return patientDAO.findAll();
    }

    @Override
    public void updateProfile(Patient patient) throws SQLException {
        patientDAO.updatePatient(patient);
    }

    @Override
    public int countTotalPatients() throws SQLException {
        return patientDAO.countAllPatients();
    }
}