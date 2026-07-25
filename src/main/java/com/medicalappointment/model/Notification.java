// File: src/com/medicalappointment/model/Notification.java
package com.medicalappointment.model;

import java.sql.Timestamp;

public class Notification {

    private int notificationId;
    private int userId;
    private Integer appointmentId;
    private String title;
    private String message;
    private NotificationType notificationType;
    private boolean read;
    private Timestamp createdAt;

    public Notification() {
    }

    public Notification(int notificationId, int userId, Integer appointmentId, String title,
                         String message, NotificationType notificationType, boolean read) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.appointmentId = appointmentId;
        this.title = title;
        this.message = message;
        this.notificationType = notificationType;
        this.read = read;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notification{notificationId=" + notificationId + ", userId=" + userId
                + ", read=" + read + "}";
    }
}