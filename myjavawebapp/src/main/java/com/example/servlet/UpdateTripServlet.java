package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.util.DatabaseManager;

public class UpdateTripServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tripId = request.getParameter("tripId");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String duration = request.getParameter("duration");
        String destination = request.getParameter("destination");
        String availability = request.getParameter("availability");
        String activityType = request.getParameter("activityType");

        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "UPDATE trips SET name = ?, description = ?, price = ?, duration = ?, destination = ?, availability = ?, activity_type = ? WHERE trip_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setDouble(3, Double.parseDouble(price));
                statement.setInt(4, Integer.parseInt(duration));
                statement.setString(5, destination);
                statement.setInt(6, Integer.parseInt(availability));
                statement.setString(7, activityType);
                statement.setInt(8, Integer.parseInt(tripId));
                statement.executeUpdate();
                response.sendRedirect("admin_dashboard.jsp");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number format: " + e.getMessage(), e);
        }
    }
}
