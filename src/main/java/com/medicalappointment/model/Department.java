// File: src/com/medicalappointment/model/Department.java
package com.medicalappointment.model;

import java.sql.Timestamp;

public class Department {

    private int departmentId;
    private String departmentName;
    private String description;
    private boolean active;
    private Timestamp createdAt;

    public Department() {
    }

    public Department(int departmentId, String departmentName, String description, boolean active) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.description = description;
        this.active = active;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
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
        return "Department{departmentId=" + departmentId + ", departmentName=" + departmentName + "}";
    }
}