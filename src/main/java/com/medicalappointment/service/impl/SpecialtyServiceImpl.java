// File: src/com/medicalappointment/service/impl/SpecialtyServiceImpl.java
package com.medicalappointment.service.impl;

import com.medicalappointment.dao.SpecialtyDAO;
import com.medicalappointment.dao.impl.SpecialtyDAOImpl;
import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.Specialty;
import com.medicalappointment.service.SpecialtyService;

import java.sql.SQLException;
import java.util.List;

public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyDAO specialtyDAO = new SpecialtyDAOImpl();

    @Override
    public Specialty getById(int specialtyId) throws SQLException, ResourceNotFoundException {
        Specialty specialty = specialtyDAO.findById(specialtyId);
        if (specialty == null) {
            throw new ResourceNotFoundException("Specialty not found.");
        }
        return specialty;
    }

    @Override
    public List<Specialty> getAll() throws SQLException {
        return specialtyDAO.findAll();
    }

    @Override
    public List<Specialty> getByDepartment(int departmentId) throws SQLException {
        return specialtyDAO.findByDepartment(departmentId);
    }

    @Override
    public int create(String name, Integer departmentId, String description)
            throws ValidationException, SQLException {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Specialty name is required.");
        }
        Specialty specialty = new Specialty();
        specialty.setSpecialtyName(name.trim());
        specialty.setDepartmentId(departmentId);
        specialty.setDescription(description);
        specialty.setActive(true);
        return specialtyDAO.createSpecialty(specialty);
    }

    @Override
    public void update(int specialtyId, String name, Integer departmentId, String description, boolean active)
            throws ValidationException, SQLException {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Specialty name is required.");
        }
        Specialty specialty = new Specialty();
        specialty.setSpecialtyId(specialtyId);
        specialty.setSpecialtyName(name.trim());
        specialty.setDepartmentId(departmentId);
        specialty.setDescription(description);
        specialty.setActive(active);
        specialtyDAO.updateSpecialty(specialty);
    }

    @Override
    public void deactivate(int specialtyId) throws SQLException {
        specialtyDAO.deleteSpecialty(specialtyId);
    }
}