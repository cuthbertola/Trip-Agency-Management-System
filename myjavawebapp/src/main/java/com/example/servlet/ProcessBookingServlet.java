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

import com.example.util.DatabaseManager;

public class ProcessBookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tripId = request.getParameter("tripId");
        String participantsParam = request.getParameter("participants");
        String tripDate = request.getParameter("tripDate");
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");
        String phoneNumber = request.getParameter("phoneNumber");

        if (tripId != null && !tripId.isEmpty() &&
            participantsParam != null && !participantsParam.isEmpty() &&
            tripDate != null && !tripDate.isEmpty() &&
            userName != null && !userName.isEmpty() &&
            userEmail != null && !userEmail.isEmpty() &&
            phoneNumber != null && !phoneNumber.isEmpty()) {

            try (Connection connection = DatabaseManager.getConnection()) {
                int participants = Integer.parseInt(participantsParam);
                
                // Check availability first
                String checkAvailabilitySql = "SELECT availability FROM trips WHERE trip_id = ?";
                try (PreparedStatement checkStmt = connection.prepareStatement(checkAvailabilitySql)) {
                    checkStmt.setInt(1, Integer.parseInt(tripId));
                    try (ResultSet resultSet = checkStmt.executeQuery()) {
                        if (resultSet.next()) {
                            int availability = resultSet.getInt("availability");
                            if (availability < participants) {
                                request.setAttribute("errorMessage", "Not enough seats available. Please reduce the number of participants.");
                                RequestDispatcher dispatcher = request.getRequestDispatcher("book_trip.jsp?tripId=" + tripId);
                                dispatcher.forward(request, response);
                                return;
                            }
                        } else {
                            request.setAttribute("errorMessage", "Trip not found.");
                            RequestDispatcher dispatcher = request.getRequestDispatcher("trip_list.jsp");
                            dispatcher.forward(request, response);
                            return;
                        }
                    }
                }

                // Insert booking into bookings table
                String bookingSql = "INSERT INTO bookings (trip_id, participants, trip_date, user_name, user_email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement bookingStmt = connection.prepareStatement(bookingSql)) {
                    bookingStmt.setInt(1, Integer.parseInt(tripId));
                    bookingStmt.setInt(2, participants);
                    bookingStmt.setString(3, tripDate);
                    bookingStmt.setString(4, userName);
                    bookingStmt.setString(5, userEmail);
                    bookingStmt.setString(6, phoneNumber);
                    bookingStmt.executeUpdate();
                }

                // Update availability in trips table
                String updateAvailabilitySql = "UPDATE trips SET availability = availability - ? WHERE trip_id = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateAvailabilitySql)) {
                    updateStmt.setInt(1, participants);
                    updateStmt.setInt(2, Integer.parseInt(tripId));
                    updateStmt.executeUpdate();
                }

                request.setAttribute("successMessage", "Trip booked successfully!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("trip_list.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Database error while booking the trip: " + e.getMessage(), e);
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid number of participants. Please enter a valid number.", e);
            }
        } else {
            request.setAttribute("errorMessage", "All fields are required. Please provide valid information.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("trip_list.jsp");
            dispatcher.forward(request, response);
        }
    }
}
