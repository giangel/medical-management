// File: src/com/medicalappointment/controller/CancelAppointmentServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
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

@WebServlet("/views/patient/appointments/cancel")
public class CancelAppointmentServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int patientId = (int) request.getSession().getAttribute(SessionConstants.PATIENT_ID);
        String reason = request.getParameter("reason");

        try {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            Appointment appointment = appointmentService.getById(appointmentId);

            if (appointment.getPatientId() != patientId) {
                request.getRequestDispatcher("/views/errors/access-denied.jsp").forward(request, response);
                return;
            }

            appointmentService.cancelAppointment(appointmentId, reason);
            response.sendRedirect(request.getContextPath() + "/views/patient/appointments");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/patient/appointments");
        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        } catch (ValidationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/views/patient/appointments.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}