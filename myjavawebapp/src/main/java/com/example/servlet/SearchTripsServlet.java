package com.example.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;  // Import the annotation
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.Trip;
import com.example.util.DatabaseManager;

@WebServlet("/searchTrips") // Add the annotation here to map this servlet to /searchTrips
public class SearchTripsServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SearchTripsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("SearchTripsServlet: Handling GET request for trip search");

        String destination = request.getParameter("destination");
        String activityType = request.getParameter("activityType");
        String priceParam = request.getParameter("maxPrice");
        String durationParam = request.getParameter("duration");

        try (Connection connection = DatabaseManager.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM trips WHERE 1=1");

            // Apply search criteria
            if (destination != null && !destination.trim().isEmpty()) {
                sql.append(" AND LOWER(destination) LIKE ?");
            }
            if (activityType != null && !activityType.trim().isEmpty()) {
                sql.append(" AND LOWER(activity_type) LIKE ?");
            }
            if (priceParam != null && !priceParam.trim().isEmpty()) {
                sql.append(" AND price <= ?");
            }
            if (durationParam != null && !durationParam.trim().isEmpty()) {
                sql.append(" AND duration <= ?");
            }

            try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
                int paramIndex = 1;

                // Set the parameters based on provided inputs
                if (destination != null && !destination.trim().isEmpty()) {
                    statement.setString(paramIndex++, "%" + destination.trim().toLowerCase() + "%");
                }
                if (activityType != null && !activityType.trim().isEmpty()) {
                    statement.setString(paramIndex++, "%" + activityType.trim().toLowerCase() + "%");
                }
                if (priceParam != null && !priceParam.trim().isEmpty()) {
                    try {
                        BigDecimal price = new BigDecimal(priceParam.trim());
                        statement.setBigDecimal(paramIndex++, price);
                    } catch (NumberFormatException e) {
                        throw new ServletException("Invalid price value. Please enter a valid number.", e);
                    }
                }
                if (durationParam != null && !durationParam.trim().isEmpty()) {
                    try {
                        int duration = Integer.parseInt(durationParam.trim());
                        statement.setInt(paramIndex++, duration);
                    } catch (NumberFormatException e) {
                        throw new ServletException("Invalid duration value. Please enter a valid number.", e);
                    }
                }

                LOGGER.log(Level.INFO, "Executing SQL query: {0}", statement);

                // Execute the query and retrieve the result
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Trip> trips = new ArrayList<>();
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

                    // Set the trips list as a request attribute to be accessed in JSP
                    request.setAttribute("trips", trips);
                    LOGGER.info("SearchTripsServlet: Set the trips attribute for the request.");

                    // Forward to dashboard.jsp for displaying the filtered trips
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp");
                    dispatcher.forward(request, response);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while retrieving trips.", e);
            throw new ServletException("Database error while retrieving trips. Please try again later.", e);
        }
    }
}
