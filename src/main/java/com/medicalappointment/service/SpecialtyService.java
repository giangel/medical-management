// File: src/com/medicalappointment/service/SpecialtyService.java
package com.medicalappointment.service;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.Specialty;

import java.sql.SQLException;
import java.util.List;

public interface SpecialtyService {
    Specialty getById(int specialtyId) throws SQLException, ResourceNotFoundException;
    List<Specialty> getAll() throws SQLException;
    List<Specialty> getByDepartment(int departmentId) throws SQLException;
    int create(String name, Integer departmentId, String description) throws ValidationException, SQLException;
    void update(int specialtyId, String name, Integer departmentId, String description, boolean active)
            throws ValidationException, SQLException;
    void deactivate(int specialtyId) throws SQLException;
}