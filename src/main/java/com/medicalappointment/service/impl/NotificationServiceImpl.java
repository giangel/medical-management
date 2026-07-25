// File: src/com/medicalappointment/service/impl/NotificationServiceImpl.java
package com.medicalappointment.service.impl;

import com.medicalappointment.dao.NotificationDAO;
import com.medicalappointment.dao.impl.NotificationDAOImpl;
import com.medicalappointment.model.Notification;
import com.medicalappointment.model.NotificationType;
import com.medicalappointment.service.NotificationService;

import java.sql.SQLException;
import java.util.List;

public class NotificationServiceImpl implements NotificationService {

    private final NotificationDAO notificationDAO = new NotificationDAOImpl();

    @Override
    public void notifyUser(int userId, Integer appointmentId, String title, String message,
                            NotificationType type) throws SQLException {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setAppointmentId(appointmentId);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(type);
        notification.setRead(false);
        notificationDAO.createNotification(notification);
    }

    @Override
    public List<Notification> getForUser(int userId) throws SQLException {
        return notificationDAO.findByUser(userId);
    }

    @Override
    public int countUnread(int userId) throws SQLException {
        return notificationDAO.countUnread(userId);
    }

    @Override
    public void markAsRead(int notificationId) throws SQLException {
        notificationDAO.markAsRead(notificationId);
    }

    @Override
    public void markAllAsRead(int userId) throws SQLException {
        notificationDAO.markAllAsRead(userId);
    }
}