// File: src/com/medicalappointment/controller/DoctorSelfProfileServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.model.Doctor;
import com.medicalappointment.model.User;
import com.medicalappointment.service.DepartmentService;
import com.medicalappointment.service.DoctorService;
import com.medicalappointment.service.SpecialtyService;
import com.medicalappointment.service.UserService;
import com.medicalappointment.service.impl.DepartmentServiceImpl;
import com.medicalappointment.service.impl.DoctorServiceImpl;
import com.medicalappointment.service.impl.SpecialtyServiceImpl;
import com.medicalappointment.service.impl.UserServiceImpl;
import com.medicalappointment.util.SessionConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/views/doctor/profile")
public class DoctorSelfProfileServlet extends HttpServlet {

    private final DoctorService doctorService = new DoctorServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final DepartmentService departmentService = new DepartmentServiceImpl();
    private final SpecialtyService specialtyService = new SpecialtyServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int doctorId = (int) request.getSession().getAttribute(SessionConstants.DOCTOR_ID);
        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            request.setAttribute("doctor", doctorService.getById(doctorId));
            request.setAttribute("user", userService.getById(userId));
            request.setAttribute("departments", departmentService.getAllActive());
            request.setAttribute("specialties", specialtyService.getAll());
            request.getRequestDispatcher("/views/doctor/profile.jsp").forward(request, response);
        } catch (SQLException | ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int doctorId = (int) request.getSession().getAttribute(SessionConstants.DOCTOR_ID);
        int userId = (int) request.getSession().getAttribute(SessionConstants.USER_ID);

        try {
            User user = userService.getById(userId);
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setPhoneNumber(request.getParameter("phoneNumber"));
            userService.updateProfile(user);

            Doctor doctor = doctorService.getById(doctorId);
            doctor.setDepartmentId(parseIntOrNull(request.getParameter("departmentId")));
            doctor.setSpecialtyId(parseIntOrNull(request.getParameter("specialtyId")));
            doctor.setLicenseNumber(request.getParameter("licenseNumber"));
            doctor.setYearsOfExperience(parseIntOrNull(request.getParameter("yearsOfExperience")));
            doctor.setBiography(request.getParameter("biography"));
            doctor.setQualifications(request.getParameter("qualifications"));

            String feeParam = request.getParameter("consultationFee");
            doctor.setConsultationFee((feeParam != null && !feeParam.isBlank()) ? new BigDecimal(feeParam) : null);

            doctorService.updateProfile(doctor);

            request.getSession().setAttribute(SessionConstants.USER_FULL_NAME, user.getFullName());
            request.setAttribute("successMessage", "Profile updated successfully.");
            request.setAttribute("doctor", doctor);
            request.setAttribute("user", user);
            request.setAttribute("departments", departmentService.getAllActive());
            request.setAttribute("specialties", specialtyService.getAll());
            request.getRequestDispatcher("/views/doctor/profile.jsp").forward(request, response);

        } catch (SQLException | ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    private Integer parseIntOrNull(String value) {
        return (value != null && !value.isBlank()) ? Integer.valueOf(value) : null;
    }
}