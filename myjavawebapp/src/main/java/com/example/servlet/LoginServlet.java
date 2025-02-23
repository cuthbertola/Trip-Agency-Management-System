package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.util.DatabaseManager;
import com.example.util.HashingUtil;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to the login page if accessed via GET request
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Logging the email and password entered for debugging purposes
        System.out.println("Email: " + email);
        System.out.println("Password entered (before hashing): " + password);

        try {
            validateParameters(email, password);
            try (Connection connection = DatabaseManager.getConnection()) {
                String salt = getSaltByEmail(connection, email);

                if (salt != null) {
                    String hashedPassword = HashingUtil.hashPasswordWithSalt(password, salt); // Hashing the input password with salt
                    System.out.println("Password after hashing with salt: " + hashedPassword);

                    int userId = getUserId(connection, email, hashedPassword);
                    if (userId != -1) {
                        System.out.println("User validated successfully. Redirecting to dashboard");

                        // Ensuring that session is properly set before redirection
                        HttpSession session = request.getSession(false);
                        if (session == null) {
                            session = request.getSession(true); // Create a new session if one does not exist
                        }
                        session.setAttribute("email", email);
                        session.setAttribute("userId", userId);
                        session.setMaxInactiveInterval(30 * 60); // Set session timeout to 30 minutes

                        // Add a log statement before redirecting
                        System.out.println("Redirecting to: " + request.getContextPath() + "/dashboard");

                        // Update the redirection target to invoke the TripListServlet first
                        response.sendRedirect(request.getContextPath() + "/dashboard");
                    } else {
                        System.out.println("Invalid email or password.");
                        response.sendRedirect("login.jsp?error=invalid_credentials");
                    }
                } else {
                    System.out.println("No salt found for the given email.");
                    response.sendRedirect("login.jsp?error=no_salt_found");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
            response.sendRedirect("login.jsp?error=invalid_input");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            response.sendRedirect("login.jsp?error=database_error");
        }
    }

    private void validateParameters(String email, String password) {
        if (isNullOrEmpty(email) || isNullOrEmpty(password)) {
            throw new IllegalArgumentException("Email and password cannot be empty.");
        }
    }

    private int getUserId(Connection connection, String email, String hashedPassword) throws SQLException {
        String sql = "SELECT user_id FROM users WHERE LOWER(email) = LOWER(?) AND hashed_password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, hashedPassword);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("User found in the database.");
                    return resultSet.getInt("user_id");
                } else {
                    System.out.println("No user found with given credentials.");
                    return -1;
                }
            }
        }
    }

    private String getSaltByEmail(Connection connection, String email) throws SQLException {
        String sql = "SELECT salt FROM users WHERE LOWER(email) = LOWER(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("salt");
                }
            }
        }
        return null;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
