// File: src/com/medicalappointment/controller/DoctorAvailabilityServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.service.DoctorService;
import com.medicalappointment.service.impl.DoctorServiceImpl;
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

@WebServlet("/views/doctor/availability")
public class DoctorAvailabilityServlet extends HttpServlet {

    private final DoctorService doctorService = new DoctorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int doctorId = (int) request.getSession().getAttribute(SessionConstants.DOCTOR_ID);

        try {
            request.setAttribute("recurringSlots", doctorService.getRecurringAvailability(doctorId));
            request.setAttribute("overrides", doctorService.getOverrides(doctorId));
            request.getRequestDispatcher("/views/doctor/availability.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int doctorId = (int) request.getSession().getAttribute(SessionConstants.DOCTOR_ID);
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "addRecurring": {
                    int dayOfWeek = Integer.parseInt(request.getParameter("dayOfWeek"));
                    Time startTime = Time.valueOf(request.getParameter("startTime") + ":00");
                    Time endTime = Time.valueOf(request.getParameter("endTime") + ":00");
                    int slotMinutes = Integer.parseInt(request.getParameter("slotMinutes"));
                    doctorService.setRecurringAvailability(doctorId, dayOfWeek, startTime, endTime, slotMinutes);
                    break;
                }
                case "addBlockedDate": {
                    Date date = Date.valueOf(request.getParameter("blockedDate"));
                    doctorService.setDateOverride(doctorId, date, true, null, null);
                    break;
                }
                case "addExtraHours": {
                    Date date = Date.valueOf(request.getParameter("extraDate"));
                    Time startTime = Time.valueOf(request.getParameter("extraStartTime") + ":00");
                    Time endTime = Time.valueOf(request.getParameter("extraEndTime") + ":00");
                    doctorService.setDateOverride(doctorId, date, false, startTime, endTime);
                    break;
                }
                default:
                    break;
            }
            response.sendRedirect(request.getContextPath() + "/views/doctor/availability");

        } catch (ValidationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            try {
                request.setAttribute("recurringSlots", doctorService.getRecurringAvailability(doctorId));
                request.setAttribute("overrides", doctorService.getOverrides(doctorId));
            } catch (SQLException ex) {
                // fall through, error page not needed since we still render the form below
            }
            request.getRequestDispatcher("/views/doctor/availability.jsp").forward(request, response);
        } catch (IllegalArgumentException | NullPointerException e) {
            response.sendRedirect(request.getContextPath() + "/views/doctor/availability");
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}