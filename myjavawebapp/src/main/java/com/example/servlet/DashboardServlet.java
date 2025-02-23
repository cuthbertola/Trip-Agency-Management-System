package com.example.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DashboardServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DashboardServlet.class.getName());

    // Constants for paths
    private static final String DASHBOARD_JSP = "dashboard.jsp";
    private static final String LOGIN_PAGE = "/login.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Log the incoming request
        LOGGER.log(Level.INFO, "Handling GET request for DashboardServlet");

        // Ensure user is logged in by checking the session
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            LOGGER.log(Level.INFO, "User is logged in, forwarding to dashboard.jsp");
            // Forward to dashboard.jsp if the user is logged in
            request.getRequestDispatcher(DASHBOARD_JSP).forward(request, response);
        } else {
            LOGGER.log(Level.WARNING, "User not logged in, redirecting to login page");
            // Redirect to login page if the user is not logged in
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
        }
    }
}
