// File: src/com/medicalappointment/controller/PatientAppointmentsServlet.java
package com.medicalappointment.controller;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/views/patient/appointments")
public class PatientAppointmentsServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int patientId = (int) request.getSession().getAttribute(SessionConstants.PATIENT_ID);

        try {
            List<Appointment> all = appointmentService.getForPatient(patientId);
            List<Appointment> upcoming = new ArrayList<>();
            List<Appointment> past = new ArrayList<>();

            for (Appointment appointment : all) {
                boolean isUpcoming = !appointment.getAppointmentDate().toLocalDate().isBefore(LocalDate.now())
                        && (appointment.getStatus().name().equals("PENDING")
                            || appointment.getStatus().name().equals("CONFIRMED"));
                if (isUpcoming) {
                    upcoming.add(appointment);
                } else {
                    past.add(appointment);
                }
            }

            request.setAttribute("upcomingAppointments", upcoming);
            request.setAttribute("pastAppointments", past);
            request.getRequestDispatcher("/views/patient/appointments.jsp").forward(request, response);

        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}