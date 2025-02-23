package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.util.DatabaseManager;
import com.example.util.HashingUtil;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection connection = DatabaseManager.getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Get hashed password and role from the database
                        String storedHash = resultSet.getString("hashed_password");
                        String role = resultSet.getString("role");

                        // Verify the password using HashingUtil
                        if (HashingUtil.verifyPassword(password, storedHash)) {
                            // Set up session
                            HttpSession session = request.getSession();
                            session.setAttribute("userRole", role);
                            session.setAttribute("username", resultSet.getString("username"));

                            // Redirect based on role
                            if ("admin".equalsIgnoreCase(role)) {
                                response.sendRedirect(request.getContextPath() + "/admin/trip");  // Redirect to admin servlet
                            } else if ("user".equalsIgnoreCase(role)) {
                                response.sendRedirect(request.getContextPath() + "/dashboard");  // Redirect to user dashboard
                            } else {
                                response.sendRedirect("login.jsp?error=invalid_credentials");
                            }
                        } else {
                            response.sendRedirect("login.jsp?error=invalid_credentials");
                        }
                    } else {
                        response.sendRedirect("login.jsp?error=invalid_credentials");
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error while logging in.", e);
        }
    }
}
