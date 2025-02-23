package com.example.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.util.DatabaseManager;

public class ProcessTripServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String tripId = request.getParameter("tripId");

        try (Connection connection = DatabaseManager.getConnection()) {

            switch (action) {
                case "add":
                    String sqlAdd = "INSERT INTO trips (trip_name, description, price, duration, destination, availability, activity_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(sqlAdd)) {
                        statement.setString(1, request.getParameter("tripName"));
                        statement.setString(2, request.getParameter("description"));
                        statement.setBigDecimal(3, new BigDecimal(request.getParameter("price")));
                        statement.setInt(4, Integer.parseInt(request.getParameter("duration")));
                        statement.setString(5, request.getParameter("destination"));
                        statement.setInt(6, Integer.parseInt(request.getParameter("availability")));
                        statement.setString(7, request.getParameter("activityType"));
                        statement.executeUpdate();
                    }
                    break;

                case "edit":
                    String sqlEdit = "UPDATE trips SET trip_name = ?, description = ?, price = ?, duration = ?, destination = ?, availability = ?, activity_type = ? WHERE trip_id = ?";
                    try (PreparedStatement statement = connection.prepareStatement(sqlEdit)) {
                        statement.setString(1, request.getParameter("tripName"));
                        statement.setString(2, request.getParameter("description"));
                        statement.setBigDecimal(3, new BigDecimal(request.getParameter("price")));
                        statement.setInt(4, Integer.parseInt(request.getParameter("duration")));
                        statement.setString(5, request.getParameter("destination"));
                        statement.setInt(6, Integer.parseInt(request.getParameter("availability")));
                        statement.setString(7, request.getParameter("activityType"));
                        statement.setInt(8, Integer.parseInt(tripId));
                        statement.executeUpdate();
                    }
                    break;

                case "delete":
                    String sqlDelete = "DELETE FROM trips WHERE trip_id = ?";
                    try (PreparedStatement statement = connection.prepareStatement(sqlDelete)) {
                        statement.setInt(1, Integer.parseInt(tripId));
                        statement.executeUpdate();
                    }
                    break;

                default:
                    throw new ServletException("Invalid action: " + action);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error while processing the trip: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid input. Please enter valid numbers for price, duration, and availability.", e);
        }
    }
}
