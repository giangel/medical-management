// File: src/com/medicalappointment/controller/ManageAppointmentsServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.service.AppointmentService;
import com.medicalappointment.service.DoctorService;
import com.medicalappointment.service.impl.AppointmentServiceImpl;
import com.medicalappointment.service.impl.DoctorServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/views/admin/appointments", "/views/admin/appointments/view"})
public class ManageAppointmentsServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();
    private final DoctorService doctorService = new DoctorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (request.getServletPath().endsWith("/view")) {
                int appointmentId = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("appointment", appointmentService.getById(appointmentId));
                request.getRequestDispatcher("/views/admin/appointment-details.jsp").forward(request, response);
                return;
            }

            Integer doctorId = parseIntOrNull(request.getParameter("doctorId"));
            String status = request.getParameter("status");
            Date fromDate = parseDateOrNull(request.getParameter("fromDate"));
            Date toDate = parseDateOrNull(request.getParameter("toDate"));

            request.setAttribute("appointments",
                    appointmentService.filter(doctorId, null, status, fromDate, toDate));
            request.setAttribute("doctors", doctorService.getAll());
            request.setAttribute("selectedDoctorId", doctorId);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("fromDate", request.getParameter("fromDate"));
            request.setAttribute("toDate", request.getParameter("toDate"));

            request.getRequestDispatcher("/views/admin/appointments.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/appointments");
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
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            String reason = request.getParameter("reason");
            appointmentService.cancelAppointment(appointmentId, reason);
            response.sendRedirect(request.getContextPath() + "/views/admin/appointments");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/appointments");
        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        } catch (ValidationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/views/admin/appointments");
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    private Integer parseIntOrNull(String value) {
        return (value != null && !value.isBlank()) ? Integer.valueOf(value) : null;
    }

    private Date parseDateOrNull(String value) {
        return (value != null && !value.isBlank()) ? Date.valueOf(value) : null;
    }
}