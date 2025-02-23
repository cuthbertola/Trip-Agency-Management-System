<%@ include file="header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Trip" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f5f5f5;
        }
        .dashboard-container {
            margin-top: 50px;
            max-width: 1200px;
            padding: 20px;
            border-radius: 15px;
            background-color: #ffffff;
            box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<div class="container dashboard-container">
    <h2 class="text-center mb-4">Admin Dashboard - Manage Trips</h2>

    <!-- Add New Trip Form -->
    <form action="/myjavawebapp/admin/trip?action=add" method="post" class="mb-4">
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="name">Trip Name:</label>
                <input type="text" class="form-control" name="name" required>
            </div>
            <div class="form-group col-md-4">
                <label for="description">Description:</label>
                <input type="text" class="form-control" name="description" required>
            </div>
            <div class="form-group col-md-4">
                <label for="price">Price:</label>
                <input type="number" class="form-control" name="price" step="0.01" required>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3">
                <label for="duration">Duration (Days):</label>
                <input type="number" class="form-control" name="duration" required>
            </div>
            <div class="form-group col-md-3">
                <label for="destination">Destination:</label>
                <input type="text" class="form-control" name="destination" required>
            </div>
            <div class="form-group col-md-3">
                <label for="availability">Availability:</label>
                <input type="number" class="form-control" name="availability" required>
            </div>
            <div class="form-group col-md-3">
                <label for="activityType">Activity Type:</label>
                <input type="text" class="form-control" name="activityType" required>
            </div>
        </div>
        <button type="submit" class="btn btn-success">Add Trip</button>
    </form>

    <!-- Existing Trips Table -->
    <h3 class="mb-3">Existing Trips</h3>
    <div class="table-responsive">
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>Trip Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Duration (Days)</th>
                    <th>Destination</th>
                    <th>Availability</th>
                    <th>Activity Type</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Trip> trips = (List<Trip>) request.getAttribute("trips");
                    if (trips != null && !trips.isEmpty()) {
                        for (Trip trip : trips) {
                %>
                    <tr>
                        <form action="/myjavawebapp/admin/trip" method="post">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="tripId" value="<%= trip.getTripId() %>">
                            <td><input type="text" name="name" value="<%= trip.getName() %>" class="form-control"></td>
                            <td><input type="text" name="description" value="<%= trip.getDescription() %>" class="form-control"></td>
                            <td><input type="number" name="price" value="<%= trip.getPrice() %>" step="0.01" class="form-control"></td>
                            <td><input type="number" name="duration" value="<%= trip.getDuration() %>" class="form-control"></td>
                            <td><input type="text" name="destination" value="<%= trip.getDestination() %>" class="form-control"></td>
                            <td><input type="number" name="availability" value="<%= trip.getAvailability() %>" class="form-control"></td>
                            <td><input type="text" name="activityType" value="<%= trip.getActivityType() %>" class="form-control"></td>
                            <td>
                                <button type="submit" class="btn btn-primary btn-sm">Update</button>
                                <a href="/myjavawebapp/admin/trip?action=delete&tripId=<%= trip.getTripId() %>" onclick="return confirm('Are you sure you want to delete this trip?');" class="btn btn-danger btn-sm">Delete</a>
                            </td>
                        </form>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="8" class="text-center">No trips available.</td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
