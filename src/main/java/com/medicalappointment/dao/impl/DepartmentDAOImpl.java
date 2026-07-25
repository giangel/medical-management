// File: src/com/medicalappointment/dao/impl/DepartmentDAOImpl.java
package com.medicalappointment.dao.impl;

import com.medicalappointment.dao.DepartmentDAO;
import com.medicalappointment.model.Department;
import com.medicalappointment.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO {

    @Override
    public int createDepartment(Department department) throws SQLException {
        String sql = "INSERT INTO departments (department_name, description, is_active) VALUES (?, ?, ?) " +
                     "RETURNING department_id";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, department.getDepartmentName());
            ps.setString(2, department.getDescription());
            ps.setBoolean(3, department.isActive());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("department_id");
                }
            }
        }
        return -1;
    }

    @Override
    public Department findById(int departmentId) throws SQLException {
        String sql = "SELECT * FROM departments WHERE department_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Department> findAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments ORDER BY department_name";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                departments.add(mapRow(rs));
            }
        }
        return departments;
    }

    @Override
    public List<Department> findAllActive() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments WHERE is_active = TRUE ORDER BY department_name";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                departments.add(mapRow(rs));
            }
        }
        return departments;
    }

    @Override
    public boolean updateDepartment(Department department) throws SQLException {
        String sql = "UPDATE departments SET department_name = ?, description = ?, is_active = ? " +
                     "WHERE department_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, department.getDepartmentName());
            ps.setString(2, department.getDescription());
            ps.setBoolean(3, department.isActive());
            ps.setInt(4, department.getDepartmentId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteDepartment(int departmentId) throws SQLException {
        // Soft delete: departments referenced by doctors/specialties cannot be hard deleted
        // without breaking history, so this deactivates the department instead.
        String sql = "UPDATE departments SET is_active = FALSE WHERE department_id = ?";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public int countAllDepartments() throws SQLException {
        String sql = "SELECT COUNT(*) FROM departments";
        try (Connection conn = DBConnectionUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private Department mapRow(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setDepartmentId(rs.getInt("department_id"));
        department.setDepartmentName(rs.getString("department_name"));
        department.setDescription(rs.getString("description"));
        department.setActive(rs.getBoolean("is_active"));
        department.setCreatedAt(rs.getTimestamp("created_at"));
        return department;
    }
}