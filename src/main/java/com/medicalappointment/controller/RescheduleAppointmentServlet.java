// File: src/com/medicalappointment/controller/RescheduleAppointmentServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.BookingConflictException;
import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.Appointment;
import com.medicalappointment.service.AppointmentService;
import com.medicalappointment.service.DoctorService;
import com.medicalappointment.service.impl.AppointmentServiceImpl;
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
import java.time.LocalDate;

@WebServlet("/views/patient/appointments/reschedule")
public class RescheduleAppointmentServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();
    private final DoctorService doctorService = new DoctorServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int patientId = (int) request.getSession().getAttribute(SessionConstants.PATIENT_ID);
        String dateParam = request.getParameter("date");

        try {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            Appointment appointment = appointmentService.getById(appointmentId);

            if (appointment.getPatientId() != patientId) {
                request.getRequestDispatcher("/views/errors/access-denied.jsp").forward(request, response);
                return;
            }

            LocalDate selectedDate = (dateParam != null && !dateParam.isBlank())
                    ? LocalDate.parse(dateParam) : LocalDate.now();
            Date sqlDate = Date.valueOf(selectedDate);

            request.setAttribute("appointment", appointment);
            request.setAttribute("selectedDate", selectedDate.toString());
            request.setAttribute("availableSlots", doctorService.getAvailableSlots(appointment.getDoctorId(), sqlDate));

            request.getRequestDispatcher("/views/patient/reschedule-appointment.jsp").forward(request, response);

        } catch (NumberFormatException | java.time.format.DateTimeParseException e) {
            response.sendRedirect(request.getContextPath() + "/views/patient/appointments");
        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int patientId = (int) request.getSession().getAttribute(SessionConstants.PATIENT_ID);
        String appointmentIdParam = request.getParameter("appointmentId");
        String dateParam = request.getParameter("date");
        String timeParam = request.getParameter("time");

        try {
            int appointmentId = Integer.parseInt(appointmentIdParam);
            Appointment original = appointmentService.getById(appointmentId);

            if (original.getPatientId() != patientId) {
                request.getRequestDispatcher("/views/errors/access-denied.jsp").forward(request, response);
                return;
            }

            Date newDate = Date.valueOf(dateParam);
            Time newStartTime = Time.valueOf(timeParam.length() == 5 ? timeParam + ":00" : timeParam);

            int newAppointmentId = appointmentService.rescheduleAppointment(appointmentId, newDate, newStartTime);
            response.sendRedirect(request.getContextPath()
                    + "/views/patient/appointment-confirmation?id=" + newAppointmentId);

        } catch (ValidationException | BookingConflictException e) {
            response.sendRedirect(request.getContextPath()
                    + "/views/patient/appointments/reschedule?appointmentId=" + appointmentIdParam
                    + "&date=" + dateParam + "&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/patient/appointments");
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}