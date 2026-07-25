// File: src/com/medicalappointment/controller/ManageSpecialtiesServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.service.DepartmentService;
import com.medicalappointment.service.SpecialtyService;
import com.medicalappointment.service.impl.DepartmentServiceImpl;
import com.medicalappointment.service.impl.SpecialtyServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/views/admin/specialties")
public class ManageSpecialtiesServlet extends HttpServlet {

    private final SpecialtyService specialtyService = new SpecialtyServiceImpl();
    private final DepartmentService departmentService = new DepartmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("specialties", specialtyService.getAll());
            request.setAttribute("departments", departmentService.getAllActive());
            request.getRequestDispatcher("/views/admin/specialties.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        Integer departmentId = (request.getParameter("departmentId") != null
                && !request.getParameter("departmentId").isBlank())
                ? Integer.valueOf(request.getParameter("departmentId")) : null;

        try {
            switch (action) {
                case "create":
                    specialtyService.create(request.getParameter("specialtyName"), departmentId,
                            request.getParameter("description"));
                    break;
                case "update":
                    int updateId = Integer.parseInt(request.getParameter("specialtyId"));
                    boolean active = Boolean.parseBoolean(request.getParameter("active"));
                    specialtyService.update(updateId, request.getParameter("specialtyName"), departmentId,
                            request.getParameter("description"), active);
                    break;
                case "deactivate":
                    specialtyService.deactivate(Integer.parseInt(request.getParameter("specialtyId")));
                    break;
                default:
                    break;
            }
            response.sendRedirect(request.getContextPath() + "/views/admin/specialties");
        } catch (ValidationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            try {
                request.setAttribute("specialties", specialtyService.getAll());
                request.setAttribute("departments", departmentService.getAllActive());
            } catch (SQLException ex) {
                // fall through
            }
            request.getRequestDispatcher("/views/admin/specialties.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/specialties");
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}