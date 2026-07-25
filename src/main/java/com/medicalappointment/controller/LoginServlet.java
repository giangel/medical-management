// File: src/com/medicalappointment/controller/LoginServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.dao.DoctorDAO;
import com.medicalappointment.dao.PatientDAO;
import com.medicalappointment.dao.impl.DoctorDAOImpl;
import com.medicalappointment.dao.impl.PatientDAOImpl;
import com.medicalappointment.exception.AuthenticationException;
import com.medicalappointment.model.Doctor;
import com.medicalappointment.model.Patient;
import com.medicalappointment.model.User;
import com.medicalappointment.service.AuthenticationService;
import com.medicalappointment.service.impl.AuthenticationServiceImpl;
import com.medicalappointment.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final AuthenticationService authService = new AuthenticationServiceImpl();
    private final DoctorDAO doctorDAO = new DoctorDAOImpl();
    private final PatientDAO patientDAO = new PatientDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession existingSession = request.getSession(false);
        if (existingSession != null && existingSession.getAttribute(SessionConstants.USER_ID) != null) {
            redirectToDashboard(request, response, (String) existingSession.getAttribute(SessionConstants.USER_ROLE));
            return;
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String redirectTarget = request.getParameter("redirect");

        try {
            User user = authService.login(email, password);

            HttpSession session = request.getSession(true);
            session.setAttribute(SessionConstants.USER_ID, user.getUserId());
            session.setAttribute(SessionConstants.USER_FULL_NAME, user.getFullName());
            session.setAttribute(SessionConstants.USER_EMAIL, user.getEmail());
            session.setAttribute(SessionConstants.USER_ROLE, user.getRoleName());

            if ("DOCTOR".equals(user.getRoleName())) {
                Doctor doctor = doctorDAO.findByUserId(user.getUserId());
                if (doctor != null) {
                    session.setAttribute(SessionConstants.DOCTOR_ID, doctor.getDoctorId());
                }
            } else if ("PATIENT".equals(user.getRoleName())) {
                Patient patient = patientDAO.findByUserId(user.getUserId());
                if (patient != null) {
                    session.setAttribute(SessionConstants.PATIENT_ID, patient.getPatientId());
                }
            }

            if (redirectTarget != null && !redirectTarget.isBlank() && redirectTarget.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + redirectTarget);
            } else {
                redirectToDashboard(request, response, user.getRoleName());
            }

        } catch (AuthenticationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("submittedEmail", email);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    private void redirectToDashboard(HttpServletRequest request, HttpServletResponse response, String role)
            throws IOException {
        String contextPath = request.getContextPath();
        switch (role) {
            case "ADMIN":
                response.sendRedirect(contextPath + "/views/admin/dashboard");
                break;
            case "DOCTOR":
                response.sendRedirect(contextPath + "/views/doctor/dashboard");
                break;
            case "PATIENT":
                response.sendRedirect(contextPath + "/views/patient/dashboard");
                break;
            default:
                response.sendRedirect(contextPath + "/login.jsp");
        }
    }
}