// File: src/com/medicalappointment/dao/AppointmentNoteDAO.java
package com.medicalappointment.dao;

import com.medicalappointment.model.AppointmentNote;
import java.sql.SQLException;
import java.util.List;

public interface AppointmentNoteDAO {
    int createNote(AppointmentNote note) throws SQLException;
    List<AppointmentNote> findByAppointment(int appointmentId) throws SQLException;
}