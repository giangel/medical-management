// File: src/com/medicalappointment/model/DoctorAvailability.java
package com.medicalappointment.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class DoctorAvailability {

    private int availabilityId;
    private int doctorId;
    private AvailabilityRecordType recordType;
    private Integer dayOfWeek; // 0 = Sunday .. 6 = Saturday, used when recordType = RECURRING
    private Date specificDate; // used when recordType = DATE_OVERRIDE
    private Time startTime;
    private Time endTime;
    private boolean unavailable;
    private int slotMinutes;
    private Timestamp createdAt;

    public DoctorAvailability() {
    }

    public DoctorAvailability(int availabilityId, int doctorId, AvailabilityRecordType recordType,
                               Integer dayOfWeek, Date specificDate, Time startTime, Time endTime,
                               boolean unavailable, int slotMinutes) {
        this.availabilityId = availabilityId;
        this.doctorId = doctorId;
        this.recordType = recordType;
        this.dayOfWeek = dayOfWeek;
        this.specificDate = specificDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.unavailable = unavailable;
        this.slotMinutes = slotMinutes;
    }

    public int getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(int availabilityId) {
        this.availabilityId = availabilityId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public AvailabilityRecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(AvailabilityRecordType recordType) {
        this.recordType = recordType;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getSpecificDate() {
        return specificDate;
    }

    public void setSpecificDate(Date specificDate) {
        this.specificDate = specificDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public boolean isUnavailable() {
        return unavailable;
    }

    public void setUnavailable(boolean unavailable) {
        this.unavailable = unavailable;
    }

    public int getSlotMinutes() {
        return slotMinutes;
    }

    public void setSlotMinutes(int slotMinutes) {
        this.slotMinutes = slotMinutes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "DoctorAvailability{availabilityId=" + availabilityId + ", doctorId=" + doctorId
                + ", recordType=" + recordType + "}";
    }
}