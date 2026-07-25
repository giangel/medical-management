// File: src/com/medicalappointment/controller/AdminDashboardServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.model.Appointment;
import com.medicalappointment.service.*;
import com.medicalappointment.service.impl.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/views/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();
    private final PatientService patientService = new PatientServiceImpl();
    private final DoctorService doctorService = new DoctorServiceImpl();
    private final AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("totalUsers", userService.countTotalUsers());
            request.setAttribute("totalPatients", patientService.countTotalPatients());
            request.setAttribute("totalDoctors", doctorService.countTotalDoctors());
            request.setAttribute("totalAppointments", appointmentService.countAll());

            Map<String, Integer> statusCounts = appointmentService.getStatusCounts();
            request.setAttribute("statusCounts", statusCounts);

            List<Appointment> all = appointmentService.getAll();
            request.setAttribute("recentAppointments", all.stream().limit(10).toList());

            request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}