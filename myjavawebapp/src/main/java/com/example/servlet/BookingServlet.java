package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.util.DatabaseManager;

public class BookingServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(BookingServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the form submission
        String tripId = request.getParameter("tripId");
        String tripDate = request.getParameter("tripDate");
        String participants = request.getParameter("participants");
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");

        // Log retrieved parameters for debugging purposes
        LOGGER.log(Level.INFO, "Received booking request with parameters: tripId={0}, tripDate={1}, participants={2}, userName={3}, userEmail={4}",
                new Object[]{tripId, tripDate, participants, userName, userEmail});

        // Validate and handle the parameters
        try {
            validateParameters(tripId, tripDate, participants, userName, userEmail);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Validation failed: {0}", e.getMessage());
            response.sendRedirect("booking.jsp?error=" + e.getMessage());
            return;
        }

        // Database operation
        try (Connection connection = DatabaseManager.getConnection()) {
            if (isTripValid(connection, tripId)) {
                addBooking(connection, tripId, tripDate, participants, userName, userEmail);
                response.sendRedirect("booking_confirmation.jsp");
            } else {
                LOGGER.log(Level.WARNING, "Invalid trip ID: {0}", tripId);
                response.sendRedirect("booking.jsp?error=Invalid trip ID. Please select a valid trip.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error: {0}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred. Please try again later.");
        }
    }

    // Validate all input parameters to ensure they are not null or empty
    private void validateParameters(String tripId, String tripDate, String participants, String userName, String userEmail) {
        LOGGER.log(Level.INFO, "Validating input parameters: tripId={0}, tripDate={1}, participants={2}, userName={3}, userEmail={4}",
                new Object[]{tripId, tripDate, participants, userName, userEmail});
        
        if (isNullOrEmpty(tripId)) {
            throw new IllegalArgumentException("Trip ID is required.");
        }
        if (isNullOrEmpty(tripDate)) {
            throw new IllegalArgumentException("Trip date is required.");
        }
        if (isNullOrEmpty(participants)) {
            throw new IllegalArgumentException("Number of participants is required.");
        }
        if (isNullOrEmpty(userName)) {
            throw new IllegalArgumentException("User name is required.");
        }
        if (isNullOrEmpty(userEmail)) {
            throw new IllegalArgumentException("User email is required.");
        }

        // Additional validation for numeric values and proper formatting
        try {
            int participantsCount = Integer.parseInt(participants);
            if (participantsCount <= 0) {
                throw new IllegalArgumentException("Number of participants must be greater than zero.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format for participants.");
        }

        if (!tripDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid date format for trip date. Use YYYY-MM-DD.");
        }

        if (!userEmail.contains("@") || !userEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
    }

    // Add booking information to the bookings table in the database
    private void addBooking(Connection connection, String tripId, String tripDate, String participants, String userName, String userEmail) throws SQLException {
        LOGGER.log(Level.INFO, "Adding booking to the database for trip ID: {0}, User: {1}", new Object[]{tripId, userName});
        
        String sql = "INSERT INTO bookings (trip_id, trip_date, participants, user_name, user_email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(tripId));
            statement.setString(2, tripDate);
            statement.setInt(3, Integer.parseInt(participants));
            statement.setString(4, userName);
            statement.setString(5, userEmail);
            statement.executeUpdate();
            LOGGER.log(Level.INFO, "Booking successful for trip ID: {0}, User: {1}", new Object[]{tripId, userName});
        }
    }

    // Check if the trip ID is valid by querying the database
    private boolean isTripValid(Connection connection, String tripId) throws SQLException {
        LOGGER.log(Level.INFO, "Checking if trip ID is valid: {0}", tripId);
        
        String sql = "SELECT COUNT(*) FROM trips WHERE trip_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(tripId));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Helper method to check if a string is null or empty
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
