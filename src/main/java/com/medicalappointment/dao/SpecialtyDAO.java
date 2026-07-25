// File: src/com/medicalappointment/dao/SpecialtyDAO.java
package com.medicalappointment.dao;

import com.medicalappointment.model.Specialty;
import java.sql.SQLException;
import java.util.List;

public interface SpecialtyDAO {
    int createSpecialty(Specialty specialty) throws SQLException;
    Specialty findById(int specialtyId) throws SQLException;
    List<Specialty> findAll() throws SQLException;
    List<Specialty> findByDepartment(int departmentId) throws SQLException;
    boolean updateSpecialty(Specialty specialty) throws SQLException;
    boolean deleteSpecialty(int specialtyId) throws SQLException;
}