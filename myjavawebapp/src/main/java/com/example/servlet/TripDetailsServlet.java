package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.Trip;
import com.example.util.DatabaseManager;

public class TripDetailsServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(TripDetailsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tripIdParam = request.getParameter("tripId");

        // Log tripId retrieval for debugging purposes
        LOGGER.log(Level.INFO, "Received tripId parameter: {0}", tripIdParam);

        if (tripIdParam == null || tripIdParam.isEmpty()) {
            LOGGER.log(Level.WARNING, "Trip ID is missing. Redirecting to trip list.");
            response.sendRedirect("trip_list.jsp");
            return;
        }

        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "SELECT * FROM trips WHERE trip_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, Integer.parseInt(tripIdParam));

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Trip trip = new Trip();
                        trip.setTripId(resultSet.getInt("trip_id"));
                        trip.setName(resultSet.getString("trip_name"));
                        trip.setDescription(resultSet.getString("description"));
                        trip.setPrice(resultSet.getBigDecimal("price"));
                        trip.setDuration(resultSet.getInt("duration"));
                        trip.setDestination(resultSet.getString("destination"));
                        trip.setAvailability(resultSet.getInt("availability"));
                        trip.setActivityType(resultSet.getString("activity_type"));

                        // Set trip attribute for JSP access
                        request.setAttribute("trip", trip);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("trip_details.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        LOGGER.log(Level.WARNING, "No trip found with ID: {0}", tripIdParam);
                        response.sendRedirect("trip_list.jsp");
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while retrieving trip details: {0}", e.getMessage());
            throw new ServletException("Database error while retrieving trip details. Please try again later.", e);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid trip ID format: {0}", e.getMessage());
            response.sendRedirect("trip_list.jsp");
        }
    }
}
