// File: src/com/medicalappointment/dao/DoctorAvailabilityDAO.java
package com.medicalappointment.dao;

import com.medicalappointment.model.DoctorAvailability;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface DoctorAvailabilityDAO {
    int createAvailability(DoctorAvailability availability) throws SQLException;
    List<DoctorAvailability> findRecurringByDoctor(int doctorId) throws SQLException;
    List<DoctorAvailability> findOverridesByDoctor(int doctorId) throws SQLException;
    DoctorAvailability findOverrideForDate(int doctorId, Date date) throws SQLException;
    boolean updateAvailability(DoctorAvailability availability) throws SQLException;
    boolean deleteAvailability(int availabilityId) throws SQLException;
}