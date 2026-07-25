// File: src/com/medicalappointment/controller/ManageUsersServlet.java
package com.medicalappointment.controller;

import com.medicalappointment.service.UserService;
import com.medicalappointment.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/views/admin/users")
public class ManageUsersServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String roleFilter = request.getParameter("role");
            if (roleFilter != null && !roleFilter.isBlank()) {
                request.setAttribute("users", userService.getByRole(roleFilter));
            } else {
                request.setAttribute("users", userService.getAll());
            }
            request.setAttribute("roleFilter", roleFilter);
            request.getRequestDispatcher("/views/admin/users.jsp").forward(request, response);
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            boolean active = Boolean.parseBoolean(request.getParameter("active"));
            userService.setAccountActive(userId, active);
            response.sendRedirect(request.getContextPath() + "/views/admin/users");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/users");
        } catch (SQLException e) {
            request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
        }
    }
}