// File: src/com/medicalappointment/controller/UpdateAppointmentStatusServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.dao.AppointmentNoteDAO;
import com.medicalappointment.dao.impl.AppointmentNoteDAOImpl;
import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.AppointmentNote;
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

@WebServlet("/views/doctor/appointments/update-status")
public class UpdateAppointmentStatusServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();
    private final AppointmentNoteDAO noteDAO = new AppointmentNoteDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int doctorId = (int) request.getSession().getAttribute(SessionConstants.DOCTOR_ID);
        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        String action = request.getParameter("action");
        String appointmentIdParam = request.getParameter("appointmentId");

        try {
            int appointmentId = Integer.parseInt(appointmentIdParam);

            switch (action) {
                case "confirm":
                    appointmentService.confirmAppointment(appointmentId, doctorId);
                    break;
                case "reject":
                    appointmentService.rejectAppointment(appointmentId, doctorId, request.getParameter("reason"));
                    break;
                case "complete":
                    appointmentService.completeAppointment(appointmentId, doctorId);
                    break;
                case "addNote": {
                    String noteText = request.getParameter("noteText");
                    if (noteText != null && !noteText.isBlank()) {
                        AppointmentNote note = new AppointmentNote();
                        note.setAppointmentId(appointmentId);
                        note.setAuthorUserId(userId);
                        note.setNoteText(noteText.trim());
                        noteDAO.createNote(note);
                    }
                    break;
                }
                default:
                    break;
            }

            response.sendRedirect(request.getContextPath()
                    + "/views/doctor/appointments/view?id=" + appointmentId);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/doctor/appointments");
        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        } catch (ValidationException e) {
            response.sendRedirect(request.getContextPath()
                    + "/views/doctor/appointments/view?id=" + appointmentIdParam
                    + "&error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}