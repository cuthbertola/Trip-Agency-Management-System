package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.util.DatabaseManager;
import com.example.util.HashingUtil;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to the registration page if accessed via GET request
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            validateParameters(username, email, password);
            try (Connection connection = DatabaseManager.getConnection()) {
                String salt = HashingUtil.generateSalt();
                String hashedPassword = HashingUtil.hashPasswordWithSalt(password, salt);

                if (registerUser(connection, username, email, hashedPassword, salt)) {
                    response.sendRedirect("login.jsp?registered=true");
                } else {
                    response.sendRedirect("register.jsp?error=true");
                }
            }
        } catch (IllegalArgumentException e) {
            throw new ServletException("Invalid input: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }

    private void validateParameters(String username, String email, String password) {
        if (isNullOrEmpty(username) || isNullOrEmpty(email) || isNullOrEmpty(password)) {
            throw new IllegalArgumentException("Username, email, and password cannot be empty.");
        }
    }

    private boolean registerUser(Connection connection, String username, String email, String hashedPassword, String salt) throws SQLException {
        String sql = "INSERT INTO users (username, email, hashed_password, salt, role) VALUES (?, ?, ?, ?, 'user')";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, hashedPassword);
            statement.setString(4, salt);
            return statement.executeUpdate() > 0;
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
