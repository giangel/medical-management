// File: src/com/medicalappointment/filter/AuthorizationFilter.java
package com.medicalappointment.filter;

import com.medicalappointment.util.SessionConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Runs after AuthenticationFilter, so a session is guaranteed to exist here.
 * Confirms the logged in user's role matches the area of the site
 * (/views/admin, /views/doctor, /views/patient) they are trying to reach,
 * so a patient cannot browse into a doctor or admin URL just by typing it.
 */
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        String role = session != null ? (String) session.getAttribute(SessionConstants.USER_ROLE) : null;

        String contextPath = httpRequest.getContextPath();
        String path = httpRequest.getRequestURI().substring(contextPath.length());

        String requiredRole = null;
        if (path.startsWith("/views/admin/")) {
            requiredRole = "ADMIN";
        } else if (path.startsWith("/views/doctor/")) {
            requiredRole = "DOCTOR";
        } else if (path.startsWith("/views/patient/")) {
            requiredRole = "PATIENT";
        }

        if (requiredRole != null && !requiredRole.equals(role)) {
            httpRequest.getRequestDispatcher("/views/errors/access-denied.jsp").forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }
}