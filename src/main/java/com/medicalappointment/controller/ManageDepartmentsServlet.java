// File: src/com/medicalappointment/controller/ManageDepartmentsServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.exception.ValidationException;
import com.medicalappointment.service.DepartmentService;
import com.medicalappointment.service.impl.DepartmentServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/views/admin/departments")
public class ManageDepartmentsServlet extends HttpServlet {

    private final DepartmentService departmentService = new DepartmentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("departments", departmentService.getAll());
            request.getRequestDispatcher("/views/admin/departments.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        try {
            switch (action) {
                case "create":
                    departmentService.create(request.getParameter("departmentName"), request.getParameter("description"));
                    break;
                case "update":
                    int updateId = Integer.parseInt(request.getParameter("departmentId"));
                    boolean active = Boolean.parseBoolean(request.getParameter("active"));
                    departmentService.update(updateId, request.getParameter("departmentName"),
                            request.getParameter("description"), active);
                    break;
                case "deactivate":
                    departmentService.deactivate(Integer.parseInt(request.getParameter("departmentId")));
                    break;
                default:
                    break;
            }
            response.sendRedirect(request.getContextPath() + "/views/admin/departments");
        } catch (ValidationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            try {
                request.setAttribute("departments", departmentService.getAll());
            } catch (SQLException ex) {
                // fall through to error page below
            }
            request.getRequestDispatcher("/views/admin/departments.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/departments");
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}