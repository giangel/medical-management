// File: src/com/medicalappointment/controller/PatientProfileServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.model.Patient;
import com.medicalappointment.model.User;
import com.medicalappointment.service.PatientService;
import com.medicalappointment.service.UserService;
import com.medicalappointment.service.impl.PatientServiceImpl;
import com.medicalappointment.service.impl.UserServiceImpl;
import com.medicalappointment.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/views/patient/profile")
public class PatientProfileServlet extends HttpServlet {

    private final PatientService patientService = new PatientServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int patientId = (int) request.getSession().getAttribute(SessionConstants.PATIENT_ID);
        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            request.setAttribute("patient", patientService.getById(patientId));
            request.setAttribute("user", userService.getById(userId));
            request.getRequestDispatcher("/views/patient/profile.jsp").forward(request, response);
        } catch (SQLException | ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int patientId = (int) request.getSession().getAttribute(SessionConstants.PATIENT_ID);
        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            User user = userService.getById(userId);
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setPhoneNumber(request.getParameter("phoneNumber"));
            user.setGender(request.getParameter("gender"));
            String dobParam = request.getParameter("dateOfBirth");
            user.setDateOfBirth((dobParam != null && !dobParam.isBlank()) ? Date.valueOf(dobParam) : null);
            userService.updateProfile(user);

            Patient patient = patientService.getById(patientId);
            patient.setAddress(request.getParameter("address"));
            patient.setEmergencyContactName(request.getParameter("emergencyContactName"));
            patient.setEmergencyContactPhone(request.getParameter("emergencyContactPhone"));
            patient.setBloodGroup(request.getParameter("bloodGroup"));
            patient.setAllergies(request.getParameter("allergies"));
            patientService.updateProfile(patient);

            request.getSession().setAttribute(SessionConstants.USER_FULL_NAME, user.getFullName());
            request.setAttribute("successMessage", "Profile updated successfully.");

            request.setAttribute("patient", patient);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/views/patient/profile.jsp").forward(request, response);

        } catch (SQLException | ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}