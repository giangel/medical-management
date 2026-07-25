// File: src/com/medicalappointment/controller/ManagePatientsServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.model.Patient;
import com.medicalappointment.service.AppointmentService;
import com.medicalappointment.service.PatientService;
import com.medicalappointment.service.UserService;
import com.medicalappointment.service.impl.AppointmentServiceImpl;
import com.medicalappointment.service.impl.PatientServiceImpl;
import com.medicalappointment.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/views/admin/patients", "/views/admin/patients/view"})
public class ManagePatientsServlet extends HttpServlet {

    private final PatientService patientService = new PatientServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (request.getServletPath().endsWith("/view")) {
                int patientId = Integer.parseInt(request.getParameter("id"));
                Patient patient = patientService.getById(patientId);
                request.setAttribute("patient", patient);
                request.setAttribute("appointments", appointmentService.filter(null, patientId, null, null, null));
                request.getRequestDispatcher("/views/admin/patient-details.jsp").forward(request, response);
            } else {
                request.setAttribute("patients", patientService.getAll());
                request.getRequestDispatcher("/views/admin/patients.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/patients");
        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            boolean active = Boolean.parseBoolean(request.getParameter("active"));
            userService.setAccountActive(userId, active);
            response.sendRedirect(request.getContextPath() + "/views/admin/patients");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/patients");
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}