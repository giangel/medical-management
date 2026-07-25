// File: src/com/medicalappointment/controller/PatientNotificationServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.service.NotificationService;
import com.medicalappointment.service.impl.NotificationServiceImpl;
import com.medicalappointment.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/views/patient/notifications")
public class PatientNotificationServlet extends HttpServlet {

    private final NotificationService notificationService = new NotificationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            request.setAttribute("notifications", notificationService.getForUser(userId));
            request.getRequestDispatcher("/views/patient/notifications.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);
        String action = request.getParameter("action");

        try {
            if ("markAllRead".equals(action)) {
                notificationService.markAllAsRead(userId);
            } else {
                String idParam = request.getParameter("notificationId");
                if (idParam != null) {
                    notificationService.markAsRead(Integer.parseInt(idParam));
                }
            }
            response.sendRedirect(request.getContextPath() + "/views/patient/notifications");
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}