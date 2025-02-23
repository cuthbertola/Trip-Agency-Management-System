<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.model.Trip" %>
<%@ page import="java.sql.*, com.example.util.DatabaseManager" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Trip</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to bottom, #f0f4f8, #d9e8f5);
            font-family: 'Poppins', sans-serif;
        }
        .booking-container {
            margin-top: 50px;
            max-width: 800px;
            padding: 30px;
            border-radius: 15px;
            background-color: #ffffff;
            box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1);
        }
        .card-header {
            background-color: #007bff;
            color: #ffffff;
            font-weight: 600;
            font-size: 1.5rem;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
            transition: all 0.3s ease-in-out;
            padding: 10px 20px;
            font-weight: 600;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #004085;
        }
        .btn-secondary {
            font-weight: 600;
        }
    </style>
</head>
<body>
<div class="container booking-container">
    <h2 class="text-center mb-4">Book Your Trip</h2>

    <%
        String tripId = request.getParameter("tripId");
        Trip trip = null;

        if (tripId != null && !tripId.isEmpty()) {
            try (Connection connection = DatabaseManager.getConnection()) {
                String sql = "SELECT * FROM trips WHERE trip_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, Integer.parseInt(tripId));
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            trip = new Trip();
                            trip.setTripId(resultSet.getInt("trip_id"));
                            trip.setName(resultSet.getString("trip_name"));
                            trip.setDescription(resultSet.getString("description"));
                            trip.setPrice(resultSet.getBigDecimal("price"));
                            trip.setDuration(resultSet.getInt("duration"));
                            trip.setDestination(resultSet.getString("destination"));
                            trip.setAvailability(resultSet.getInt("availability"));
                            trip.setActivityType(resultSet.getString("activity_type"));
                        }
                    }
                }
            } catch (SQLException e) {
                out.println("<p class='text-danger'>Database error: " + e.getMessage() + "</p>");
            }
        }

        if (trip != null) {
    %>
        <div class="card mb-4">
            <div class="card-header">
                <h3><%= trip.getName() %></h3>
            </div>
            <div class="card-body">
                <p><strong>Description:</strong> <%= trip.getDescription() %></p>
                <p><strong>Price:</strong> $<%= trip.getPrice() %> per person</p>
                <p><strong>Duration:</strong> <%= trip.getDuration() %> days</p>
                <p><strong>Destination:</strong> <%= trip.getDestination() %></p>
                <p><strong>Availability:</strong> <%= trip.getAvailability() %> seats left</p>
                <p><strong>Activity Type:</strong> <%= trip.getActivityType() %></p>
            </div>
        </div>

        <form action="processBooking" method="post">
            <input type="hidden" name="tripId" value="<%= tripId %>">
            <div class="form-group mb-3">
                <label for="tripDate" class="form-label">Trip Date:</label>
                <input type="date" class="form-control" id="tripDate" name="tripDate" required>
            </div>
            <div class="form-group mb-3">
                <label for="participants" class="form-label">Number of Participants:</label>
                <input type="number" class="form-control" id="participants" name="participants" min="1" max="<%= trip.getAvailability() %>" required>
            </div>
            <div class="form-group mb-3">
                <label for="userName" class="form-label">Your Name:</label>
                <input type="text" class="form-control" id="userName" name="userName" placeholder="Full Name" required>
            </div>
            <div class="form-group mb-3">
                <label for="userEmail" class="form-label">Your Email:</label>
                <input type="email" class="form-control" id="userEmail" name="userEmail" placeholder="Email Address" required>
            </div>
            <div class="form-group mb-4">
                <label for="phoneNumber" class="form-label">Contact Number:</label>
                <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="Phone Number" required>
            </div>
            <button type="submit" class="btn btn-primary">Book Now</button>
            <a href="trip_list.jsp" class="btn btn-secondary">Back to Available Trips</a>
        </form>
    <%
        } else {
    %>
        <p class="text-center text-danger">No trip information available. Please try again.</p>
        <a href="trip_list.jsp" class="btn btn-secondary">Back to Available Trips</a>
    <%
        }
    %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
