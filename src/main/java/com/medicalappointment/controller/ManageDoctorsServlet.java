// File: src/com/medicalappointment/controller/ManageDoctorsServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ResourceNotFoundException;
import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.model.Doctor;
import com.medicalappointment.service.*;
import com.medicalappointment.service.impl.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet(urlPatterns = {
        "/views/admin/doctors",
        "/views/admin/doctors/add",
        "/views/admin/doctors/edit",
        "/views/admin/doctors/view",
        "/views/admin/doctors/toggle-status"
})
public class ManageDoctorsServlet extends HttpServlet {

    private final DoctorService doctorService = new DoctorServiceImpl();
    private final DepartmentService departmentService = new DepartmentServiceImpl();
    private final SpecialtyService specialtyService = new SpecialtyServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final AppointmentService appointmentService = new AppointmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        try {
            if (path.endsWith("/add")) {
                request.setAttribute("departments", departmentService.getAllActive());
                request.setAttribute("specialties", specialtyService.getAll());
                request.getRequestDispatcher("/views/admin/doctor-form.jsp").forward(request, response);

            } else if (path.endsWith("/edit")) {
                int doctorId = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("doctor", doctorService.getById(doctorId));
                request.setAttribute("departments", departmentService.getAllActive());
                request.setAttribute("specialties", specialtyService.getAll());
                request.getRequestDispatcher("/views/admin/doctor-form.jsp").forward(request, response);

            } else if (path.endsWith("/view")) {
                int doctorId = Integer.parseInt(request.getParameter("id"));
                Doctor doctor = doctorService.getById(doctorId);
                request.setAttribute("doctor", doctor);
                request.setAttribute("appointments", appointmentService.getForDoctor(doctorId));
                request.getRequestDispatcher("/views/admin/doctor-details.jsp").forward(request, response);

            } else {
                request.setAttribute("doctors", doctorService.getAll());
                request.getRequestDispatcher("/views/admin/doctors.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/doctors");
        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        try {
            if (path.endsWith("/add")) {
                handleAdd(request, response);
            } else if (path.endsWith("/edit")) {
                handleEdit(request, response);
            } else if (path.endsWith("/toggle-status")) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                boolean active = Boolean.parseBoolean(request.getParameter("active"));
                userService.setAccountActive(userId, active);
                response.sendRedirect(request.getContextPath() + "/views/admin/doctors");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/doctors");
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String temporaryPassword = request.getParameter("temporaryPassword");
        String licenseNumber = request.getParameter("licenseNumber");
        Integer departmentId = parseIntOrNull(request.getParameter("departmentId"));
        Integer specialtyId = parseIntOrNull(request.getParameter("specialtyId"));

        try {
            doctorService.registerDoctor(firstName, lastName, email, phoneNumber, temporaryPassword,
                    departmentId, specialtyId, licenseNumber);
            response.sendRedirect(request.getContextPath() + "/views/admin/doctors");
        } catch (ValidationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("departments", departmentService.getAllActive());
            request.setAttribute("specialties", specialtyService.getAll());
            request.getRequestDispatcher("/views/admin/doctor-form.jsp").forward(request, response);
        }
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            Doctor doctor = doctorService.getById(doctorId);

            doctor.setDepartmentId(parseIntOrNull(request.getParameter("departmentId")));
            doctor.setSpecialtyId(parseIntOrNull(request.getParameter("specialtyId")));
            doctor.setLicenseNumber(request.getParameter("licenseNumber"));
            doctor.setYearsOfExperience(parseIntOrNull(request.getParameter("yearsOfExperience")));
            doctor.setBiography(request.getParameter("biography"));
            doctor.setQualifications(request.getParameter("qualifications"));

            String feeParam = request.getParameter("consultationFee");
            doctor.setConsultationFee((feeParam != null && !feeParam.isBlank()) ? new BigDecimal(feeParam) : null);

            String slotParam = request.getParameter("defaultSlotMinutes");
            doctor.setDefaultSlotMinutes((slotParam != null && !slotParam.isBlank()) ? Integer.parseInt(slotParam) : 30);

            doctorService.updateProfile(doctor);
            response.sendRedirect(request.getContextPath() + "/views/admin/doctors");
        } catch (ResourceNotFoundException e) {
            request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
        }
    }

    private Integer parseIntOrNull(String value) {
        return (value != null && !value.isBlank()) ? Integer.valueOf(value) : null;
    }
}