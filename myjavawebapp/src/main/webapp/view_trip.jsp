<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trip Details - Trip Agency Management System</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>

<body>
    <div class="container mt-5">
        <div class="card shadow-lg p-4">
            <h2>Trip Details</h2>
            <hr>
            <p><strong>Trip Name:</strong> <%= request.getParameter("tripName") %></p>
            <p><strong>Description:</strong> <%= request.getParameter("description") %></p>
            <p><strong>Price:</strong> $<%= request.getParameter("price") %></p>
            <p><strong>Duration:</strong> <%= request.getParameter("duration") %> days</p>
            <p><strong>Destination:</strong> <%= request.getParameter("destination") %></p>
            <p><strong>Availability:</strong> <%= request.getParameter("availability") %> seats left</p>
            <p><strong>Activity Type:</strong> <%= request.getParameter("activityType") %></p>

            <a href="book_trip.jsp?tripId=<%= request.getParameter("tripId") %>&tripName=<%= request.getParameter("tripName") %>" class="btn btn-success mt-3">Book This Trip</a>
            <a href="dashboard.jsp" class="btn btn-secondary mt-3">Back to Dashboard</a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
