<%@ include file="header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Trip</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(to bottom, #f0f4f8, #d9e8f5);
        }
        .container {
            margin-top: 50px;
            max-width: 600px;
            padding: 20px;
            border-radius: 15px;
            background-color: #ffffff;
            box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #333;
            font-weight: 600;
        }
        .form-group label {
            font-weight: 500;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-secondary {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center mb-4">Add a New Trip</h2>
    <form action="processTrip" method="post">
        <input type="hidden" name="action" value="add">

        <!-- Trip Name Field -->
        <div class="form-group mb-3">
            <label for="name">Trip Name:</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>

        <!-- Description Field -->
        <div class="form-group mb-3">
            <label for="description">Description:</label>
            <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
        </div>

        <!-- Price Field -->
        <div class="form-group mb-3">
            <label for="price">Price (in USD):</label>
            <input type="number" class="form-control" id="price" name="price" step="0.01" required>
        </div>

        <!-- Duration Field -->
        <div class="form-group mb-3">
            <label for="duration">Duration (in days):</label>
            <input type="number" class="form-control" id="duration" name="duration" required>
        </div>

        <!-- Destination Field -->
        <div class="form-group mb-3">
            <label for="destination">Destination:</label>
            <input type="text" class="form-control" id="destination" name="destination" required>
        </div>

        <!-- Availability Field -->
        <div class="form-group mb-3">
            <label for="availability">Availability (Number of Seats):</label>
            <input type="number" class="form-control" id="availability" name="availability" required>
        </div>

        <!-- Activity Type Field -->
        <div class="form-group mb-3">
            <label for="activityType">Activity Type:</label>
            <input type="text" class="form-control" id="activityType" name="activityType" required>
        </div>

        <!-- Add Trip Button and Back Button -->
        <button type="submit" class="btn btn-primary">Add Trip</button>
        <a href="admin_dashboard.jsp" class="btn btn-secondary">Back to Dashboard</a>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
