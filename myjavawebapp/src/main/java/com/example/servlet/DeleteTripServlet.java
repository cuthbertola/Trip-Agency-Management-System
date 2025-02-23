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

public class DeleteTripServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DeleteTripServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve tripId parameter from the request
        String tripId = request.getParameter("tripId");

        // Validate tripId parameter
        if (tripId == null || tripId.isEmpty()) {
            LOGGER.log(Level.WARNING, "Trip ID is missing for deletion.");
            request.setAttribute("errorMessage", "Trip ID is missing. Please try again.");
            response.sendRedirect("admin_dashboard.jsp");
            return;
        }

        // Database operation to delete the trip
        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "DELETE FROM trips WHERE trip_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, Integer.parseInt(tripId));

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    LOGGER.log(Level.INFO, "Trip successfully deleted with ID: {0}", tripId);
                    response.sendRedirect("admin_dashboard.jsp?success=delete");
                } else {
                    LOGGER.log(Level.WARNING, "No trip found with ID: {0}", tripId);
                    response.sendRedirect("admin_dashboard.jsp?error=notfound");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while deleting trip with ID: {0}", tripId);
            throw new ServletException("Database error occurred while deleting the trip. Please try again later.", e);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid trip ID format: {0}", e.getMessage());
            throw new ServletException("Invalid number format for trip ID. Please try again.", e);
        }
    }
}
