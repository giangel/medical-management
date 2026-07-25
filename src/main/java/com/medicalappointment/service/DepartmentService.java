// File: src/com/medicalappointment/service/DepartmentService.java
package com.medicalappointment.service;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.Department;

import java.sql.SQLException;
import java.util.List;

public interface DepartmentService {
    Department getById(int departmentId) throws SQLException, ResourceNotFoundException;
    List<Department> getAll() throws SQLException;
    List<Department> getAllActive() throws SQLException;
    int create(String name, String description) throws ValidationException, SQLException;
    void update(int departmentId, String name, String description, boolean active)
            throws ValidationException, SQLException;
    void deactivate(int departmentId) throws SQLException;
}