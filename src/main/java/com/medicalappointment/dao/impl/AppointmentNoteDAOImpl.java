// File: src/com/medicalappointment/dao/impl/AppointmentNoteDAOImpl.java
package com.medicalappointment.dao.impl;

import com.medicalappointment.dao.AppointmentNoteDAO;
import com.medicalappointment.model.AppointmentNote;
import com.medicalappointment.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentNoteDAOImpl implements AppointmentNoteDAO {

    @Override
    public int createNote(AppointmentNote note) throws SQLException {
        String sql = "INSERT INTO appointment_notes (appointment_id, author_user_id, note_text) " +
                     "VALUES (?, ?, ?) RETURNING note_id";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, note.getAppointmentId());
            ps.setInt(2, note.getAuthorUserId());
            ps.setString(3, note.getNoteText());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("note_id");
                }
            }
        }
        return -1;
    }

    @Override
    public List<AppointmentNote> findByAppointment(int appointmentId) throws SQLException {
        List<AppointmentNote> notes = new ArrayList<>();
        String sql = "SELECT n.note_id, n.appointment_id, n.author_user_id, n.note_text, n.created_at, " +
                     "u.first_name, u.last_name FROM appointment_notes n " +
                     "JOIN users u ON n.author_user_id = u.user_id " +
                     "WHERE n.appointment_id = ? ORDER BY n.created_at";
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AppointmentNote note = new AppointmentNote();
                    note.setNoteId(rs.getInt("note_id"));
                    note.setAppointmentId(rs.getInt("appointment_id"));
                    note.setAuthorUserId(rs.getInt("author_user_id"));
                    note.setNoteText(rs.getString("note_text"));
                    note.setCreatedAt(rs.getTimestamp("created_at"));
                    note.setAuthorFullName(rs.getString("first_name") + " " + rs.getString("last_name"));
                    notes.add(note);
                }
            }
        }
        return notes;
    }
}