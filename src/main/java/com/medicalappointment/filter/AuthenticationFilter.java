// File: src/com/medicalappointment/filter/AuthenticationFilter.java
package com.medicalappointment.filter;

import com.medicalappointment.util.SessionConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Ensures every request into a role-specific area of the site has a logged
 * in user attached to the session. Registered in web.xml ahead of
 * AuthorizationFilter so a missing login is always caught first.
 */
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        boolean loggedIn = session != null && session.getAttribute(SessionConstants.USER_ID) != null;

        if (!loggedIn) {
            String contextPath = httpRequest.getContextPath();
            String requestedUrl = httpRequest.getRequestURI().substring(contextPath.length());
            httpResponse.sendRedirect(contextPath + "/login.jsp?redirect="
                    + java.net.URLEncoder.encode(requestedUrl, "UTF-8"));
            return;
        }

        chain.doFilter(request, response);
    }
}