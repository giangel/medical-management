// File: src/com/medicalappointment/controller/DoctorDashboardServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.model.Appointment;
import com.medicalappointment.service.AppointmentService;
import com.medicalappointment.service.DoctorService;
import com.medicalappointment.service.NotificationService;
import com.medicalappointment.service.impl.AppointmentServiceImpl;
import com.medicalappointment.service.impl.DoctorServiceImpl;
import com.medicalappointment.service.impl.NotificationServiceImpl;
import com.medicalappointment.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/views/doctor/dashboard")
public class DoctorDashboardServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();
    private final DoctorService doctorService = new DoctorServiceImpl();
    private final NotificationService notificationService = new NotificationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int doctorId = (int) request.getSession().getAttribute(SessionConstants.DOCTOR_ID);
        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            Date today = Date.valueOf(LocalDate.now());
            List<Appointment> todayAppointments = appointmentService.getForDoctorAndDate(doctorId, today);
            List<Appointment> allAppointments = appointmentService.getForDoctor(doctorId);

            List<Appointment> pendingRequests = allAppointments.stream()
                    .filter(a -> a.getStatus().name().equals("PENDING"))
                    .toList();

            List<Appointment> upcoming = allAppointments.stream()
                    .filter(a -> a.getStatus().name().equals("CONFIRMED"))
                    .filter(a -> !a.getAppointmentDate().toLocalDate().isBefore(LocalDate.now()))
                    .toList();

            long completedCount = allAppointments.stream()
                    .filter(a -> a.getStatus().name().equals("COMPLETED"))
                    .count();

            request.setAttribute("doctor", doctorService.getById(doctorId));
            request.setAttribute("todayAppointments", todayAppointments);
            request.setAttribute("pendingRequests", pendingRequests);
            request.setAttribute("upcomingAppointments", upcoming);
            request.setAttribute("completedCount", completedCount);
            request.setAttribute("unreadNotifications", notificationService.countUnread(userId));

            request.getRequestDispatcher("/views/doctor/dashboard.jsp").forward(request, response);
        } catch (SQLException | ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}