// File: src/com/medicalappointment/dao/impl/SpecialtyDAOImpl.java
package com.medicalappointment.dao.impl;

import com.medicalappointment.dao.SpecialtyDAO;
import com.medicalappointment.model.Specialty;
import com.medicalappointment.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialtyDAOImpl implements SpecialtyDAO {

    private static final String SELECT_BASE =
        "SELECT s.specialty_id, s.specialty_name, s.department_id, s.description, s.is_active, " +
        "s.created_at, d.department_name FROM specialties s " +
        "LEFT JOIN departments d ON s.department_id = d.department_id ";

    @Override
    public int createSpecialty(Specialty specialty) throws SQLException {
        String sql = "INSERT INTO specialties (specialty_name, department_id, description, is_active) " +
                     "VALUES (?, ?, ?, ?) RETURNING specialty_id";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, specialty.getSpecialtyName());
            if (specialty.getDepartmentId() != null) {
                ps.setInt(2, specialty.getDepartmentId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setString(3, specialty.getDescription());
            ps.setBoolean(4, specialty.isActive());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("specialty_id");
                }
            }
        }
        return -1;
    }

    @Override
    public Specialty findById(int specialtyId) throws SQLException {
        String sql = SELECT_BASE + "WHERE s.specialty_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, specialtyId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Specialty> findAll() throws SQLException {
        List<Specialty> specialties = new ArrayList<>();
        String sql = SELECT_BASE + "ORDER BY s.specialty_name";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                specialties.add(mapRow(rs));
            }
        }
        return specialties;
    }

    @Override
    public List<Specialty> findByDepartment(int departmentId) throws SQLException {
        List<Specialty> specialties = new ArrayList<>();
        String sql = SELECT_BASE + "WHERE s.department_id = ? ORDER BY s.specialty_name";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    specialties.add(mapRow(rs));
                }
            }
        }
        return specialties;
    }

    @Override
    public boolean updateSpecialty(Specialty specialty) throws SQLException {
        String sql = "UPDATE specialties SET specialty_name = ?, department_id = ?, description = ?, " +
                     "is_active = ? WHERE specialty_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, specialty.getSpecialtyName());
            if (specialty.getDepartmentId() != null) {
                ps.setInt(2, specialty.getDepartmentId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setString(3, specialty.getDescription());
            ps.setBoolean(4, specialty.isActive());
            ps.setInt(5, specialty.getSpecialtyId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteSpecialty(int specialtyId) throws SQLException {
        String sql = "UPDATE specialties SET is_active = FALSE WHERE specialty_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, specialtyId);
            return ps.executeUpdate() > 0;
        }
    }

    private Specialty mapRow(ResultSet rs) throws SQLException {
        Specialty specialty = new Specialty();
        specialty.setSpecialtyId(rs.getInt("specialty_id"));
        specialty.setSpecialtyName(rs.getString("specialty_name"));
        specialty.setDepartmentId((Integer) rs.getObject("department_id"));
        specialty.setDepartmentName(rs.getString("department_name"));
        specialty.setDescription(rs.getString("description"));
        specialty.setActive(rs.getBoolean("is_active"));
        specialty.setCreatedAt(rs.getTimestamp("created_at"));
        return specialty;
    }
}