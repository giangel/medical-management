// File: src/com/medicalappointment/controller/PatientDashboardServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.model.Appointment;
import com.medicalappointment.model.Patient;
import com.medicalappointment.service.AppointmentService;
import com.medicalappointment.service.NotificationService;
import com.medicalappointment.service.PatientService;
import com.medicalappointment.service.impl.AppointmentServiceImpl;
import com.medicalappointment.service.impl.NotificationServiceImpl;
import com.medicalappointment.service.impl.PatientServiceImpl;
import com.medicalappointment.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/views/patient/dashboard")
public class PatientDashboardServlet extends HttpServlet {

    private final AppointmentService appointmentService = new AppointmentServiceImpl();
    private final PatientService patientService = new PatientServiceImpl();
    private final NotificationService notificationService = new NotificationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int patientId = (int) request.getSession().getAttribute(SessionConstants.PATIENT_ID);
        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            List<Appointment> allAppointments = appointmentService.getForPatient(patientId);
            Appointment nextAppointment = allAppointments.stream()
                    .filter(a -> a.getStatus().name().equals("PENDING") || a.getStatus().name().equals("CONFIRMED"))
                    .filter(a -> !a.getAppointmentDate().toLocalDate().isBefore(java.time.LocalDate.now()))
                    .min((a, b) -> {
                        int dateCompare = a.getAppointmentDate().compareTo(b.getAppointmentDate());
                        return dateCompare != 0 ? dateCompare : a.getStartTime().compareTo(b.getStartTime());
                    })
                    .orElse(null);

            request.setAttribute("nextAppointment", nextAppointment);
            request.setAttribute("upcomingCount", appointmentService.countUpcomingForPatient(patientId));
            request.setAttribute("recentAppointments", allAppointments.stream().limit(5).toList());
            request.setAttribute("unreadNotifications", notificationService.countUnread(userId));

            Patient patient = patientService.getById(patientId);
            request.setAttribute("patient", patient);

            request.getRequestDispatcher("/views/patient/dashboard.jsp").forward(request, response);
        } catch (SQLException | com.medicalappointment.exception.ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}