// File: src/com/medicalappointment/controller/DoctorSearchServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.service.DepartmentService;
import com.medicalappointment.service.DoctorService;
import com.medicalappointment.service.SpecialtyService;
import com.medicalappointment.service.impl.DepartmentServiceImpl;
import com.medicalappointment.service.impl.DoctorServiceImpl;
import com.medicalappointment.service.impl.SpecialtyServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/views/patient/doctors")
public class DoctorSearchServlet extends HttpServlet {

    private final DoctorService doctorService = new DoctorServiceImpl();
    private final DepartmentService departmentService = new DepartmentServiceImpl();
    private final SpecialtyService specialtyService = new SpecialtyServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String departmentParam = request.getParameter("departmentId");
        String specialtyParam = request.getParameter("specialtyId");

        Integer departmentId = (departmentParam != null && !departmentParam.isBlank())
                ? Integer.valueOf(departmentParam) : null;
        Integer specialtyId = (specialtyParam != null && !specialtyParam.isBlank())
                ? Integer.valueOf(specialtyParam) : null;

        try {
            request.setAttribute("doctors", doctorService.search(keyword, departmentId, specialtyId));
            request.setAttribute("departments", departmentService.getAllActive());
            request.setAttribute("specialties", specialtyService.getAll());
            request.setAttribute("keyword", keyword);
            request.setAttribute("selectedDepartmentId", departmentId);
            request.setAttribute("selectedSpecialtyId", specialtyId);

            request.getRequestDispatcher("/views/patient/doctor-search.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}