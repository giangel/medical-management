// File: src/com/medicalappointment/controller/DoctorAppointmentsServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.model.Appointment;
import com.medicalappointment.model.AppointmentNote;
import com.medicalappointment.service.AppointmentService;
import com.medicalappointment.service.impl.AppointmentServiceImpl;
import com.medicalappointment.dao.AppointmentNoteDAO;
import com.medicalappointment.dao.impl.AppointmentNoteDAOImpl;
import com.medicalappointment.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/views/doctor/appointments", "/views/doctor/appointments/view"})
public class DoctorAppointmentsServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();
    private final AppointmentNoteDAO noteDAO = new AppointmentNoteDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int doctorId = (int) request.getSession().getAttribute(SessionConstants.DOCTOR_ID);

        try {
            if (request.getServletPath().endsWith("/view")) {
                int appointmentId = Integer.parseInt(request.getParameter("id"));
                Appointment appointment = appointmentService.getById(appointmentId);

                if (appointment.getDoctorId() != doctorId) {
                    request.getRequestDispatcher("/views/errors/access-denied.jsp").forward(request, response);
                    return;
                }

                List<AppointmentNote> notes = noteDAO.findByAppointment(appointmentId);
                request.setAttribute("appointment", appointment);
                request.setAttribute("notes", notes);
                request.getRequestDispatcher("/views/doctor/appointment-details.jsp").forward(request, response);
                return;
            }

            String status = request.getParameter("status");
            String dateParam = request.getParameter("date");
            Date filterDate = (dateParam != null && !dateParam.isBlank()) ? Date.valueOf(dateParam) : null;

            List<Appointment> appointments = appointmentService.filter(doctorId, null, status, filterDate, filterDate);
            request.setAttribute("appointments", appointments);
            request.setAttribute("selectedStatus", status);
            request.setAttribute("selectedDate", dateParam);

            request.getRequestDispatcher("/views/doctor/appointments.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/doctor/appointments");
        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}