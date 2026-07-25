// File: src/com/medicalappointment/controller/DoctorProfileServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.service.DoctorService;
import com.medicalappointment.service.impl.DoctorServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/views/patient/doctor")
public class DoctorProfileServlet extends HttpServlet {

    private final DoctorService doctorService = new DoctorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String dateParam = request.getParameter("date");

        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/views/patient/doctors");
            return;
        }

        try {
            int doctorId = Integer.parseInt(idParam);
            request.setAttribute("doctor", doctorService.getById(doctorId));

            LocalDate selectedDate = (dateParam != null && !dateParam.isBlank())
                    ? LocalDate.parse(dateParam) : LocalDate.now();
            Date sqlDate = Date.valueOf(selectedDate);

            request.setAttribute("selectedDate", selectedDate.toString());
            request.setAttribute("availableSlots", doctorService.getAvailableSlots(doctorId, sqlDate));

            request.getRequestDispatcher("/views/patient/doctor-profile.jsp").forward(request, response);
        } catch (NumberFormatException | java.time.format.DateTimeParseException e) {
            response.sendRedirect(request.getContextPath() + "/views/patient/doctors");
        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}