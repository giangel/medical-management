// File: src/com/medicalappointment/controller/BookAppointmentServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.BookingConflictException;
import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.service.AppointmentService;
import com.medicalappointment.service.impl.AppointmentServiceImpl;
import com.medicalappointment.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

@WebServlet("/views/patient/book")
public class BookAppointmentServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int patientId = (int) request.getSession().getAttribute(SessionConstants.PATIENT_ID);

        String doctorIdParam = request.getParameter("doctorId");
        String dateParam = request.getParameter("date");
        String timeParam = request.getParameter("time");
        String reason = request.getParameter("reason");

        try {
            int doctorId = Integer.parseInt(doctorIdParam);
            Date date = Date.valueOf(dateParam);
            Time startTime = Time.valueOf(timeParam.length() == 5 ? timeParam + ":00" : timeParam);

            int appointmentId = appointmentService.bookAppointment(patientId, doctorId, date, startTime, reason);

            response.sendRedirect(request.getContextPath()
                    + "/views/patient/appointment-confirmation?id=" + appointmentId);

        } catch (ValidationException | BookingConflictException e) {
            request.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath()
                    + "/views/patient/doctor?id=" + doctorIdParam + "&date=" + dateParam
                    + "&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            response.sendRedirect(request.getContextPath() + "/views/patient/doctors");
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}