// File: src/com/medicalappointment/service/NotificationService.java
package com.medicalappointment.service;

import com.medicalappointment.model.Notification;
import com.medicalappointment.model.NotificationType;

import java.sql.SQLException;
import java.util.List;

public interface NotificationService {
    void notifyUser(int userId, Integer appointmentId, String title, String message, NotificationType type)
            throws SQLException;
    List<Notification> getForUser(int userId) throws SQLException;
    int countUnread(int userId) throws SQLException;
    void markAsRead(int notificationId) throws SQLException;
    void markAllAsRead(int userId) throws SQLException;
}