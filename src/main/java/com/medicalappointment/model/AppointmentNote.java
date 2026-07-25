// File: src/com/medicalappointment/model/AppointmentNote.java
package com.medicalappointment.model;

import java.sql.Timestamp;

public class AppointmentNote {

    private int noteId;
    private int appointmentId;
    private int authorUserId;
    private String noteText;
    private Timestamp createdAt;

    // Convenience field populated by join, not a column on this table
    private String authorFullName;

    public AppointmentNote() {
    }

    public AppointmentNote(int noteId, int appointmentId, int authorUserId, String noteText) {
        this.noteId = noteId;
        this.appointmentId = appointmentId;
        this.authorUserId = authorUserId;
        this.noteText = noteText;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(int authorUserId) {
        this.authorUserId = authorUserId;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public void setAuthorFullName(String authorFullName) {
        this.authorFullName = authorFullName;
    }

    @Override
    public String toString() {
        return "AppointmentNote{noteId=" + noteId + ", appointmentId=" + appointmentId + "}";
    }
}