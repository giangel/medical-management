// File: src/com/medicalappointment/dao/NotificationDAO.java
package com.medicalappointment.dao;

import com.medicalappointment.model.Notification;
import java.sql.SQLException;
import java.util.List;

public interface NotificationDAO {
    int createNotification(Notification notification) throws SQLException;
    List<Notification> findByUser(int userId) throws SQLException;
    int countUnread(int userId) throws SQLException;
    boolean markAsRead(int notificationId) throws SQLException;
    boolean markAllAsRead(int userId) throws SQLException;
}