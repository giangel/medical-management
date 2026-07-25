// File: src/com/medicalappointment/model/AuditLog.java
package com.medicalappointment.model;

import java.sql.Timestamp;

public class AuditLog {

    private int logId;
    private Integer userId;
    private String actionType;
    private String description;
    private String ipAddress;
    private Timestamp createdAt;

    public AuditLog() {
    }

    public AuditLog(int logId, Integer userId, String actionType, String description, String ipAddress) {
        this.logId = logId;
        this.userId = userId;
        this.actionType = actionType;
        this.description = description;
        this.ipAddress = ipAddress;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AuditLog{logId=" + logId + ", actionType=" + actionType + "}";
    }
}