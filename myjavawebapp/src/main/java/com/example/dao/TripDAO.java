package com.example.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Trip;
import com.example.util.DatabaseManager;

public class TripDAO {

    // Method to search for trips with optional filtering criteria
    public List<Trip> searchTrips(String destination, Integer minDuration, Integer maxDuration, BigDecimal minPrice, BigDecimal maxPrice, String activityType) throws SQLException {
        // Constructing the base query
        String sql = "SELECT * FROM trips WHERE 1=1";
        List<Object> parameters = new ArrayList<>();

        // Adding filtering conditions
        if (destination != null && !destination.isEmpty()) {
            sql += " AND destination LIKE ?";
            parameters.add("%" + destination + "%");
        }
        if (minDuration != null) {
            sql += " AND duration >= ?";
            parameters.add(minDuration);
        }
        if (maxDuration != null) {
            sql += " AND duration <= ?";
            parameters.add(maxDuration);
        }
        if (minPrice != null) {
            sql += " AND price >= ?";
            parameters.add(minPrice);
        }
        if (maxPrice != null) {
            sql += " AND price <= ?";
            parameters.add(maxPrice);
        }
        if (activityType != null && !activityType.isEmpty()) {
            sql += " AND activity_type LIKE ?";
            parameters.add("%" + activityType + "%");
        }

        // Connect to the database and execute the query
        try (Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set parameters in the query
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            ResultSet resultSet = statement.executeQuery();

            // Populate the list of trips from the result set
            List<Trip> trips = new ArrayList<>();
            while (resultSet.next()) {
                Trip trip = new Trip();
                trip.setTripId(resultSet.getInt("trip_id"));
                trip.setName(resultSet.getString("trip_name")); // Updated to use setName() instead of setTripName()
                trip.setDescription(resultSet.getString("description"));
                trip.setPrice(resultSet.getBigDecimal("price"));
                trip.setDuration(resultSet.getInt("duration"));
                trip.setDestination(resultSet.getString("destination"));
                trip.setAvailability(resultSet.getInt("availability"));
                trip.setActivityType(resultSet.getString("activity_type"));
                trips.add(trip);
            }

            return trips;
        }
    }
}