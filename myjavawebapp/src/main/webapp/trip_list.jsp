<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Trip" %>
<!DOCTYPE html>
<html>
<head>
    <title>Available Trips</title>
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
            max-width: 1200px;
            padding: 20px;
            border-radius: 15px;
            background-color: #ffffff;
            box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1);
        }
        .table thead {
            background-color: #343a40;
            color: #ffffff;
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
    <h2 class="text-center mb-4">Available Trips</h2>

    <!-- Search Form -->
    <form action="searchTrips" method="get" class="mb-4">
        <div class="form-row">
            <div class="form-group col-md-3">
                <label for="destination">Destination</label>
                <input type="text" class="form-control" name="destination" id="destination" placeholder="Destination">
            </div>
            <div class="form-group col-md-3">
                <label for="duration">Duration (Days)</label>
                <input type="number" class="form-control" name="duration" id="duration" placeholder="Duration">
            </div>
            <div class="form-group col-md-3">
                <label for="price">Price (Max)</label>
                <input type="number" class="form-control" name="price" id="price" placeholder="Price">
            </div>
            <div class="form-group col-md-3">
                <label for="activityType">Activity Type</label>
                <input type="text" class="form-control" name="activityType" id="activityType" placeholder="Activity Type">
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <!-- Trip List -->
    <div class="table-responsive">
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>Name</th>
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
                        <td><%= trip.getName() %></td> <!-- Updated from getTripName() to getName() -->
                        <td><%= trip.getDescription() %></td>
                        <td>$<%= trip.getPrice() %></td>
                        <td><%= trip.getDuration() %></td>
                        <td><%= trip.getDestination() %></td>
                        <td><%= trip.getAvailability() %> seats left</td>
                        <td><%= trip.getActivityType() %></td>
                        <td>
                            <a href="tripDetails?tripId=<%= trip.getTripId() %>" class="btn btn-info btn-sm">View</a>
                            <a href="edit_trip.jsp?tripId=<%= trip.getTripId() %>&name=<%= trip.getName() %>&description=<%= trip.getDescription() %>&price=<%= trip.getPrice() %>&duration=<%= trip.getDuration() %>&destination=<%= trip.getDestination() %>&availability=<%= trip.getAvailability() %>&activityType=<%= trip.getActivityType() %>" class="btn btn-warning btn-sm">Edit</a>
                            <a href="/admin/trip?action=delete&tripId=<%= trip.getTripId() %>" onclick="return confirm('Are you sure you want to delete this trip?');" class="btn btn-danger btn-sm">Delete</a>
                        </td>
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
    <a href="add_trip.jsp" class="btn btn-success">Add New Trip</a>
    <a href="dashboard.jsp" class="btn btn-secondary">Back to Dashboard</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
