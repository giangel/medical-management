// File: src/com/medicalappointment/dao/impl/PatientDAOImpl.java
package com.medicalappointment.dao.impl;

import com.medicalappointment.dao.PatientDAO;
import com.medicalappointment.model.Patient;
import com.medicalappointment.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {

    private static final String SELECT_BASE =
        "SELECT p.patient_id, p.user_id, p.address, p.emergency_contact_name, " +
        "p.emergency_contact_phone, p.blood_group, p.allergies, p.created_at, p.updated_at, " +
        "u.first_name, u.last_name, u.email, u.phone_number " +
        "FROM patients p JOIN users u ON p.user_id = u.user_id ";

    @Override
    public int createPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (user_id, address, emergency_contact_name, " +
                     "emergency_contact_phone, blood_group, allergies) VALUES (?, ?, ?, ?, ?, ?) " +
                     "RETURNING patient_id";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patient.getUserId());
            ps.setString(2, patient.getAddress());
            ps.setString(3, patient.getEmergencyContactName());
            ps.setString(4, patient.getEmergencyContactPhone());
            ps.setString(5, patient.getBloodGroup());
            ps.setString(6, patient.getAllergies());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("patient_id");
                }
            }
        }
        return -1;
    }

    @Override
    public Patient findById(int patientId) throws SQLException {
        String sql = SELECT_BASE + "WHERE p.patient_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Patient findByUserId(int userId) throws SQLException {
        String sql = SELECT_BASE + "WHERE p.user_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET address = ?, emergency_contact_name = ?, " +
                     "emergency_contact_phone = ?, blood_group = ?, allergies = ? WHERE patient_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patient.getAddress());
            ps.setString(2, patient.getEmergencyContactName());
            ps.setString(3, patient.getEmergencyContactPhone());
            ps.setString(4, patient.getBloodGroup());
            ps.setString(5, patient.getAllergies());
            ps.setInt(6, patient.getPatientId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Patient> findAll() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = SELECT_BASE + "ORDER BY p.created_at DESC";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                patients.add(mapRow(rs));
            }
        }
        return patients;
    }

    @Override
    public int countAllPatients() throws SQLException {
        String sql = "SELECT COUNT(*) FROM patients";
        try (Connection conn = DBConnectionUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private Patient mapRow(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setPatientId(rs.getInt("patient_id"));
        patient.setUserId(rs.getInt("user_id"));
        patient.setAddress(rs.getString("address"));
        patient.setEmergencyContactName(rs.getString("emergency_contact_name"));
        patient.setEmergencyContactPhone(rs.getString("emergency_contact_phone"));
        patient.setBloodGroup(rs.getString("blood_group"));
        patient.setAllergies(rs.getString("allergies"));
        patient.setCreatedAt(rs.getTimestamp("created_at"));
        patient.setUpdatedAt(rs.getTimestamp("updated_at"));
        patient.setFirstName(rs.getString("first_name"));
        patient.setLastName(rs.getString("last_name"));
        patient.setEmail(rs.getString("email"));
        patient.setPhoneNumber(rs.getString("phone_number"));
        return patient;
    }
}