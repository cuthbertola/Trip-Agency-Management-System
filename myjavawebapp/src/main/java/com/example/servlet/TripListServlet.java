package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.Trip;
import com.example.util.DatabaseManager;

public class TripListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Log to confirm servlet invocation
        System.out.println("TripListServlet: Handling GET request for /dashboard");

        String destination = request.getParameter("destination");
        String activityType = request.getParameter("activityType");
        String maxPriceStr = request.getParameter("maxPrice");
        String durationStr = request.getParameter("duration");

        List<Trip> trips;

        try {
            // Retrieve the list of trips from the database based on search criteria
            trips = getTrips(destination, activityType, maxPriceStr, durationStr);

            // Log each trip to verify data retrieval
            for (Trip trip : trips) {
                System.out.println("Retrieved Trip: ID = " + trip.getTripId() +
                        ", Name = " + trip.getName() +
                        ", Destination = " + trip.getDestination() +
                        ", Price = " + trip.getPrice() +
                        ", Duration = " + trip.getDuration() +
                        ", Availability = " + trip.getAvailability() +
                        ", Activity Type = " + trip.getActivityType());
            }

            // Set the trips list as a request attribute to be accessed in JSP
            request.setAttribute("trips", trips);
            System.out.println("TripListServlet: Set the trips attribute for the request.");

        } catch (SQLException e) {
            // Log the error and set an error attribute for the JSP page to display
            System.err.println("Database error while retrieving trips: " + e.getMessage());
            request.setAttribute("errorMessage", "Unable to retrieve trips. Please try again later.");
        }

        // Forward to dashboard.jsp to display the list of trips
        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
        dispatcher.forward(request, response);
    }

    private List<Trip> getTrips(String destination, String activityType, String maxPriceStr, String durationStr) throws SQLException {
        List<Trip> trips = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM trips WHERE 1=1");

        // Add filters based on the provided parameters
        boolean hasFilters = false;

        if (destination != null && !destination.trim().isEmpty()) {
            sql.append(" AND LOWER(destination) LIKE ?");
            hasFilters = true;
        }
        if (activityType != null && !activityType.trim().isEmpty()) {
            sql.append(" AND LOWER(activity_type) LIKE ?");
            hasFilters = true;
        }
        if (maxPriceStr != null && !maxPriceStr.trim().isEmpty()) {
            sql.append(" AND price <= ?");
            hasFilters = true;
        }
        if (durationStr != null && !durationStr.trim().isEmpty()) {
            sql.append(" AND duration <= ?");
            hasFilters = true;
        }

        // If no filters are provided, log that all trips will be retrieved
        if (!hasFilters) {
            System.out.println("No search criteria provided. Retrieving all trips.");
        }

        try (Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            if (destination != null && !destination.trim().isEmpty()) {
                statement.setString(paramIndex++, "%" + destination.toLowerCase() + "%");
            }
            if (activityType != null && !activityType.trim().isEmpty()) {
                statement.setString(paramIndex++, "%" + activityType.toLowerCase() + "%");
            }
            if (maxPriceStr != null && !maxPriceStr.trim().isEmpty()) {
                statement.setBigDecimal(paramIndex++, new java.math.BigDecimal(maxPriceStr));
            }
            if (durationStr != null && !durationStr.trim().isEmpty()) {
                statement.setInt(paramIndex++, Integer.parseInt(durationStr));
            }

            // Log to confirm SQL statement is executed
            System.out.println("Executing SQL query: " + statement);

            try (ResultSet resultSet = statement.executeQuery()) {
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

                // Log the number of trips retrieved
                System.out.println("Number of trips retrieved: " + trips.size());
            }
        } catch (SQLException e) {
            System.err.println("SQL Error while executing query: " + e.getMessage());
            throw e;
        }

        return trips;
    }
}
