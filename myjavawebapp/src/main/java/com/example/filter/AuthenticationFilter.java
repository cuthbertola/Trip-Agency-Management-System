package com.example.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if required
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // Allow access to login page, register page, or any resources (like CSS, images, etc.)
        if (requestURI.endsWith("login.jsp") || requestURI.endsWith("register.jsp")
                || requestURI.contains("/resources/") || requestURI.endsWith("/login")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);

        // Check if the user session exists or if the user is logged in when accessing protected pages
        if ((requestURI.endsWith("dashboard.jsp") || requestURI.contains("/trip-list"))
                && (session == null || session.getAttribute("userId") == null)) {
            // Redirect to login page if not logged in and trying to access a protected page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        // Admin-specific access control
        if ((requestURI.endsWith("admin_dashboard.jsp") || requestURI.contains("/admin/"))
                && (session == null || !"admin".equals(session.getAttribute("userRole")))) {
            // Redirect to login page if not logged in as admin and trying to access admin page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        // Continue to the requested resource if logged in or accessing allowed pages
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code if needed
    }
}
