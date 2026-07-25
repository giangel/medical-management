// File: src/com/medicalappointment/dao/DepartmentDAO.java
package com.medicalappointment.dao;

import com.medicalappointment.model.Department;
import java.sql.SQLException;
import java.util.List;

public interface DepartmentDAO {
    int createDepartment(Department department) throws SQLException;
    Department findById(int departmentId) throws SQLException;
    List<Department> findAll() throws SQLException;
    List<Department> findAllActive() throws SQLException;
    boolean updateDepartment(Department department) throws SQLException;
    boolean deleteDepartment(int departmentId) throws SQLException;
    int countAllDepartments() throws SQLException;
}