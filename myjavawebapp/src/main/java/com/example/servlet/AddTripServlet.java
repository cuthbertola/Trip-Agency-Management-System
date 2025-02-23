package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.util.DatabaseManager;

public class AddTripServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddTripServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the form
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String duration = request.getParameter("duration");
        String destination = request.getParameter("destination");
        String availability = request.getParameter("availability");
        String activityType = request.getParameter("activityType");

        // Validate input parameters
        if (isNullOrEmpty(name) || isNullOrEmpty(description) || isNullOrEmpty(price) ||
            isNullOrEmpty(duration) || isNullOrEmpty(destination) ||
            isNullOrEmpty(availability) || isNullOrEmpty(activityType)) {

            LOGGER.log(Level.WARNING, "Missing input data while adding a new trip.");
            request.setAttribute("errorMessage", "All fields are required. Please fill in all details.");
            request.getRequestDispatcher("/add_trip.jsp").forward(request, response);
            return;
        }

        // Database insertion
        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "INSERT INTO trips (name, description, price, duration, destination, availability, activity_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setDouble(3, Double.parseDouble(price));
                statement.setInt(4, Integer.parseInt(duration));
                statement.setString(5, destination);
                statement.setInt(6, Integer.parseInt(availability));
                statement.setString(7, activityType);

                statement.executeUpdate();
                LOGGER.log(Level.INFO, "Trip successfully added: {0}", name);
                response.sendRedirect("admin_dashboard.jsp?success=true");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while adding a new trip: {0}", e.getMessage());
            throw new ServletException("Database error occurred while adding the trip. Please try again later.", e);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid number format: {0}", e.getMessage());
            request.setAttribute("errorMessage", "Invalid number format for price, duration, or availability. Please check your input values.");
            request.getRequestDispatcher("/add_trip.jsp").forward(request, response);
        }
    }

    // Helper method to check if a string is null or empty
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
