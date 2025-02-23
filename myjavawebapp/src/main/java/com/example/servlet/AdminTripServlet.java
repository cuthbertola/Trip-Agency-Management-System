package com.example.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.Trip;
import com.example.util.DatabaseManager;

@WebServlet("/admin/trip") // Using annotation for servlet mapping
public class AdminTripServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Trip> trips = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "SELECT * FROM trips";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Trip trip = new Trip();
                    trip.setTripId(resultSet.getInt("trip_id"));
                    trip.setName(resultSet.getString("trip_name"));
                    trip.setDescription(resultSet.getString("description"));
                    trip.setPrice(resultSet.getBigDecimal("price"));
                    trip.setDuration(resultSet.getInt("duration"));
                    trip.setDestination(resultSet.getString("destination"));
                    trip.setAvailability(resultSet.getInt("availability"));
                    trip.setActivityType(resultSet.getString("activity_type"));
                    trips.add(trip);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }

        request.setAttribute("trips", trips);
        request.getRequestDispatcher("/admin_dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String tripId = request.getParameter("tripId");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String duration = request.getParameter("duration");
        String destination = request.getParameter("destination");
        String availability = request.getParameter("availability");
        String activityType = request.getParameter("activityType");

        try (Connection connection = DatabaseManager.getConnection()) {
            switch (action.toLowerCase()) {
                case "add":
                    addTrip(connection, name, description, price, duration, destination, availability, activityType);
                    break;

                case "update":
                    updateTrip(connection, tripId, name, description, price, duration, destination, availability, activityType);
                    break;

                case "delete":
                    deleteTrip(connection, tripId);
                    break;

                default:
                    throw new ServletException("Invalid action: " + action);
            }

            response.sendRedirect(request.getContextPath() + "/admin/trip");
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }

    private void addTrip(Connection connection, String name, String description, String price, String duration, String destination, String availability, String activityType) throws SQLException {
        if (isNullOrEmpty(name) || isNullOrEmpty(description) || isNullOrEmpty(price) || isNullOrEmpty(duration) || isNullOrEmpty(destination) || isNullOrEmpty(availability) || isNullOrEmpty(activityType)) {
            throw new SQLException("All fields are required to add a new trip.");
        }

        String sqlAdd = "INSERT INTO trips (trip_name, description, price, duration, destination, availability, activity_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statementAdd = connection.prepareStatement(sqlAdd)) {
            statementAdd.setString(1, name);
            statementAdd.setString(2, description);
            statementAdd.setBigDecimal(3, parseBigDecimalSafe(price, "Price"));
            statementAdd.setInt(4, parseIntSafe(duration, "Duration"));
            statementAdd.setString(5, destination);
            statementAdd.setInt(6, parseIntSafe(availability, "Availability"));
            statementAdd.setString(7, activityType);
            statementAdd.executeUpdate();
        }
    }

    private void updateTrip(Connection connection, String tripId, String name, String description, String price, String duration, String destination, String availability, String activityType) throws SQLException {
        if (isNullOrEmpty(tripId) || isNullOrEmpty(name) || isNullOrEmpty(description) || isNullOrEmpty(price) || isNullOrEmpty(duration) || isNullOrEmpty(destination) || isNullOrEmpty(availability) || isNullOrEmpty(activityType)) {
            throw new SQLException("All fields are required to update a trip.");
        }

        String sqlUpdate = "UPDATE trips SET trip_name=?, description=?, price=?, duration=?, destination=?, availability=?, activity_type=? WHERE trip_id=?";
        try (PreparedStatement statementUpdate = connection.prepareStatement(sqlUpdate)) {
            statementUpdate.setString(1, name);
            statementUpdate.setString(2, description);
            statementUpdate.setBigDecimal(3, parseBigDecimalSafe(price, "Price"));
            statementUpdate.setInt(4, parseIntSafe(duration, "Duration"));
            statementUpdate.setString(5, destination);
            statementUpdate.setInt(6, parseIntSafe(availability, "Availability"));
            statementUpdate.setString(7, activityType);
            statementUpdate.setInt(8, parseIntSafe(tripId, "Trip ID"));
            statementUpdate.executeUpdate();
        }
    }

    private void deleteTrip(Connection connection, String tripId) throws SQLException {
        if (isNullOrEmpty(tripId)) {
            throw new SQLException("Trip ID is required to delete a trip.");
        }

        String sqlDelete = "DELETE FROM trips WHERE trip_id=?";
        try (PreparedStatement statementDelete = connection.prepareStatement(sqlDelete)) {
            statementDelete.setInt(1, parseIntSafe(tripId, "Trip ID"));
            statementDelete.executeUpdate();
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private BigDecimal parseBigDecimalSafe(String value, String fieldName) throws SQLException {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new SQLException("Invalid number format for " + fieldName + ": " + value);
        }
    }

    private int parseIntSafe(String value, String fieldName) throws SQLException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new SQLException("Invalid number format for " + fieldName + ": " + value);
        }
    }
}
