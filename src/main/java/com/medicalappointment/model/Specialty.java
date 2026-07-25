// File: src/com/medicalappointment/model/Specialty.java
package com.medicalappointment.model;

import java.sql.Timestamp;

public class Specialty {

    private int specialtyId;
    private String specialtyName;
    private Integer departmentId;
    private String departmentName; // convenience field populated by joins
    private String description;
    private boolean active;
    private Timestamp createdAt;

    public Specialty() {
    }

    public Specialty(int specialtyId, String specialtyName, Integer departmentId, String description, boolean active) {
        this.specialtyId = specialtyId;
        this.specialtyName = specialtyName;
        this.departmentId = departmentId;
        this.description = description;
        this.active = active;
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Specialty{specialtyId=" + specialtyId + ", specialtyName=" + specialtyName + "}";
    }
}