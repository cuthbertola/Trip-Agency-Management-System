<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.model.Trip" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trip Details</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to bottom, #f0f4f8, #d9e8f5);
            font-family: 'Poppins', sans-serif;
        }
        .container {
            margin-top: 50px;
            max-width: 800px;
            padding: 20px;
            border-radius: 15px;
            background-color: #ffffff;
            box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1);
        }
        .btn-primary, .btn-success, .btn-warning, .btn-danger {
            transition: all 0.3s ease-in-out;
            font-weight: 600;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #004085;
        }
        .btn-success:hover {
            background-color: #218838;
            border-color: #1e7e34;
        }
        .btn-warning:hover {
            background-color: #ffc107;
        }
        .btn-danger:hover {
            background-color: #c82333;
            border-color: #bd2130;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">Trip Details</h2>

    <%
        // Retrieve the trip object from the request attribute
        Trip trip = (Trip) request.getAttribute("trip");
        if (trip != null) {
    %>
    <div class="card">
        <div class="card-body">
            <h3 class="card-title"><%= trip.getName() %></h3>
            <p class="card-text"><strong>Description:</strong> <%= trip.getDescription() %></p>
            <p class="card-text"><strong>Price:</strong> $<%= trip.getPrice() %></p>
            <p class="card-text"><strong>Duration (Days):</strong> <%= trip.getDuration() %></p>
            <p class="card-text"><strong>Destination:</strong> <%= trip.getDestination() %></p>
            <p class="card-text"><strong>Availability:</strong> <%= trip.getAvailability() %> seats left</p>
            <p class="card-text"><strong>Activity Type:</strong> <%= trip.getActivityType() %></p>

            <!-- Booking Form -->
            <form action="<%= request.getContextPath() %>/book" method="post" class="mt-4">
                <input type="hidden" name="tripId" value="<%= trip.getTripId() %>">
                <div class="form-group">
                    <label for="tripDate">Trip Date:</label>
                    <input type="date" name="tripDate" id="tripDate" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="participants">Number of Participants:</label>
                    <input type="number" name="participants" id="participants" class="form-control" min="1" max="<%= trip.getAvailability() %>" required>
                </div>
                <input type="hidden" name="userId" value="${sessionScope.userId}">
                <div class="form-group">
                    <label for="userName">Your Name:</label>
                    <input type="text" name="userName" id="userName" class="form-control" value="${sessionScope.userName}" required>
                </div>
                <div class="form-group">
                    <label for="userEmail">Your Email:</label>
                    <input type="email" name="userEmail" id="userEmail" class="form-control" value="${sessionScope.userEmail}" required>
                </div>
                <button type="submit" class="btn btn-success mt-3">Book Now</button>
            </form>
        </div>
    </div>
    <%
        } else {
    %>
    <div class="alert alert-danger text-center">
        <strong>Error:</strong> Trip details could not be retrieved. Please try again later.
    </div>
    <%
        }
    %>
    <a href="<%= request.getContextPath() %>/dashboard" class="btn btn-secondary mt-4">Back to Dashboard</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
