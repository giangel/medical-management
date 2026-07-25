// File: src/com/medicalappointment/dao/impl/DoctorDAOImpl.java
package com.medicalappointment.dao.impl;

import com.medicalappointment.dao.DoctorDAO;
import com.medicalappointment.model.Doctor;
import com.medicalappointment.util.DBConnectionUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO {

    private static final String SELECT_BASE =
        "SELECT doc.doctor_id, doc.user_id, doc.department_id, doc.specialty_id, doc.license_number, " +
        "doc.years_of_experience, doc.biography, doc.qualifications, doc.consultation_fee, " +
        "doc.default_slot_minutes, doc.is_accepting_appointments, doc.created_at, doc.updated_at, " +
        "u.first_name, u.last_name, u.email, u.phone_number, u.profile_image, " +
        "dep.department_name, spec.specialty_name " +
        "FROM doctors doc " +
        "JOIN users u ON doc.user_id = u.user_id " +
        "LEFT JOIN departments dep ON doc.department_id = dep.department_id " +
        "LEFT JOIN specialties spec ON doc.specialty_id = spec.specialty_id ";

    @Override
    public int createDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (user_id, department_id, specialty_id, license_number, " +
                     "years_of_experience, biography, qualifications, consultation_fee, " +
                     "default_slot_minutes, is_accepting_appointments) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                     "RETURNING doctor_id";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctor.getUserId());
            setNullableInt(ps, 2, doctor.getDepartmentId());
            setNullableInt(ps, 3, doctor.getSpecialtyId());
            ps.setString(4, doctor.getLicenseNumber());
            setNullableInt(ps, 5, doctor.getYearsOfExperience());
            ps.setString(6, doctor.getBiography());
            ps.setString(7, doctor.getQualifications());
            ps.setBigDecimal(8, doctor.getConsultationFee());
            ps.setInt(9, doctor.getDefaultSlotMinutes());
            ps.setBoolean(10, doctor.isAcceptingAppointments());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("doctor_id");
                }
            }
        }
        return -1;
    }

    @Override
    public Doctor findById(int doctorId) throws SQLException {
        String sql = SELECT_BASE + "WHERE doc.doctor_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Doctor findByUserId(int userId) throws SQLException {
        String sql = SELECT_BASE + "WHERE doc.user_id = ?";
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
    public boolean updateDoctor(Doctor doctor) throws SQLException {
        String sql = "UPDATE doctors SET department_id = ?, specialty_id = ?, license_number = ?, " +
                     "years_of_experience = ?, biography = ?, qualifications = ?, consultation_fee = ?, " +
                     "default_slot_minutes = ? WHERE doctor_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            setNullableInt(ps, 1, doctor.getDepartmentId());
            setNullableInt(ps, 2, doctor.getSpecialtyId());
            ps.setString(3, doctor.getLicenseNumber());
            setNullableInt(ps, 4, doctor.getYearsOfExperience());
            ps.setString(5, doctor.getBiography());
            ps.setString(6, doctor.getQualifications());
            ps.setBigDecimal(7, doctor.getConsultationFee());
            ps.setInt(8, doctor.getDefaultSlotMinutes());
            ps.setInt(9, doctor.getDoctorId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean setAcceptingAppointments(int doctorId, boolean accepting) throws SQLException {
        String sql = "UPDATE doctors SET is_accepting_appointments = ? WHERE doctor_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, accepting);
            ps.setInt(2, doctorId);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Doctor> findAll() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = SELECT_BASE + "ORDER BY u.last_name";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                doctors.add(mapRow(rs));
            }
        }
        return doctors;
    }

    @Override
    public List<Doctor> search(String keyword, Integer departmentId, Integer specialtyId) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        StringBuilder sql = new StringBuilder(SELECT_BASE).append("WHERE doc.is_accepting_appointments = TRUE ");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isBlank()) {
            sql.append("AND (LOWER(u.first_name) LIKE ? OR LOWER(u.last_name) LIKE ?) ");
            String pattern = "%" + keyword.toLowerCase() + "%";
            params.add(pattern);
            params.add(pattern);
        }
        if (departmentId != null) {
            sql.append("AND doc.department_id = ? ");
            params.add(departmentId);
        }
        if (specialtyId != null) {
            sql.append("AND doc.specialty_id = ? ");
            params.add(specialtyId);
        }
        sql.append("ORDER BY u.last_name");

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    doctors.add(mapRow(rs));
                }
            }
        }
        return doctors;
    }

    @Override
    public int countAllDoctors() throws SQLException {
        String sql = "SELECT COUNT(*) FROM doctors";
        try (Connection conn = DBConnectionUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private void setNullableInt(PreparedStatement ps, int index, Integer value) throws SQLException {
        if (value != null) {
            ps.setInt(index, value);
        } else {
            ps.setNull(index, Types.INTEGER);
        }
    }

    private Doctor mapRow(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(rs.getInt("doctor_id"));
        doctor.setUserId(rs.getInt("user_id"));
        doctor.setDepartmentId((Integer) rs.getObject("department_id"));
        doctor.setSpecialtyId((Integer) rs.getObject("specialty_id"));
        doctor.setLicenseNumber(rs.getString("license_number"));
        doctor.setYearsOfExperience((Integer) rs.getObject("years_of_experience"));
        doctor.setBiography(rs.getString("biography"));
        doctor.setQualifications(rs.getString("qualifications"));
        BigDecimal fee = rs.getBigDecimal("consultation_fee");
        doctor.setConsultationFee(fee);
        doctor.setDefaultSlotMinutes(rs.getInt("default_slot_minutes"));
        doctor.setAcceptingAppointments(rs.getBoolean("is_accepting_appointments"));
        doctor.setCreatedAt(rs.getTimestamp("created_at"));
        doctor.setUpdatedAt(rs.getTimestamp("updated_at"));
        doctor.setFirstName(rs.getString("first_name"));
        doctor.setLastName(rs.getString("last_name"));
        doctor.setEmail(rs.getString("email"));
        doctor.setPhoneNumber(rs.getString("phone_number"));
        doctor.setProfileImage(rs.getString("profile_image"));
        doctor.setDepartmentName(rs.getString("department_name"));
        doctor.setSpecialtyName(rs.getString("specialty_name"));
        return doctor;
    }
}