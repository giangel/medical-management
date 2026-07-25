// File: src/com/medicalappointment/service/impl/DepartmentServiceImpl.java
package com.medicalappointment.service.impl;

import com.medicalappointment.dao.DepartmentDAO;
import com.medicalappointment.dao.impl.DepartmentDAOImpl;
import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.Department;
import com.medicalappointment.service.DepartmentService;

import java.sql.SQLException;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDAO departmentDAO = new DepartmentDAOImpl();

    @Override
    public Department getById(int departmentId) throws SQLException, ResourceNotFoundException {
        Department department = departmentDAO.findById(departmentId);
        if (department == null) {
            throw new ResourceNotFoundException("Department not found.");
        }
        return department;
    }

    @Override
    public List<Department> getAll() throws SQLException {
        return departmentDAO.findAll();
    }

    @Override
    public List<Department> getAllActive() throws SQLException {
        return departmentDAO.findAllActive();
    }

    @Override
    public int create(String name, String description) throws ValidationException, SQLException {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Department name is required.");
        }
        Department department = new Department();
        department.setDepartmentName(name.trim());
        department.setDescription(description);
        department.setActive(true);
        return departmentDAO.createDepartment(department);
    }

    @Override
    public void update(int departmentId, String name, String description, boolean active)
            throws ValidationException, SQLException {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Department name is required.");
        }
        Department department = new Department();
        department.setDepartmentId(departmentId);
        department.setDepartmentName(name.trim());
        department.setDescription(description);
        department.setActive(active);
        departmentDAO.updateDepartment(department);
    }

    @Override
    public void deactivate(int departmentId) throws SQLException {
        departmentDAO.deleteDepartment(departmentId);
    }
}