package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/trip_agency_db"; // Ensure this matches your MySQL instance and database
    private static final String USER = "root"; // Update with your correct MySQL username if different
    private static final String PASSWORD = "Hazardeous16!"; // Replace with your updated MySQL password

    static {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver successfully loaded.");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load MySQL JDBC driver. Please check if the MySQL connector is in the classpath.");
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    // Get a connection to the database
    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection successful to " + URL);
            return connection;
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database. Please verify the URL, username, and password.");
            throw e;
        }
    }

    // Close the connection
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Failed to close connection: " + e.getMessage());
            }
        }
    }

    // Main method for quick testing
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Database connection successful!");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
