// File: src/com/medicalappointment/controller/AppointmentConfirmationServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.model.Appointment;
import com.medicalappointment.service.AppointmentService;
import com.medicalappointment.service.impl.AppointmentServiceImpl;
import com.medicalappointment.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/views/patient/appointment-confirmation")
public class AppointmentConfirmationServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int patientId = (int) request.getSession().getAttribute(SessionConstants.PATIENT_ID);

        try {
            int appointmentId = Integer.parseInt(request.getParameter("id"));
            Appointment appointment = appointmentService.getById(appointmentId);

            if (appointment.getPatientId() != patientId) {
                request.getRequestDispatcher("/views/errors/access-denied.jsp").forward(request, response);
                return;
            }

            request.setAttribute("appointment", appointment);
            request.getRequestDispatcher("/views/patient/appointment-confirmation.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/patient/appointments");
        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}