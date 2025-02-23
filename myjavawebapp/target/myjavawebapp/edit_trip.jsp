<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Trip</title>
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
        .btn-primary, .btn-secondary {
            transition: all 0.3s ease-in-out;
            font-weight: 600;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #004085;
        }
        .btn-secondary:hover {
            background-color: #6c757d;
            border-color: #5a6268;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">Edit Trip</h2>
    <form action="/admin/trip" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="tripId" value="<%= request.getParameter("tripId") %>">

        <div class="form-group">
            <label for="name">Trip Name:</label>
            <input type="text" class="form-control" id="name" name="name" value="<%= request.getParameter("name") %>" required>
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <textarea class="form-control" id="description" name="description" rows="3" required><%= request.getParameter("description") %></textarea>
        </div>

        <div class="form-group">
            <label for="price">Price:</label>
            <input type="number" class="form-control" id="price" name="price" step="0.01" min="0" value="<%= request.getParameter("price") %>" required>
        </div>

        <div class="form-group">
            <label for="duration">Duration (Days):</label>
            <input type="number" class="form-control" id="duration" name="duration" min="1" value="<%= request.getParameter("duration") %>" required>
        </div>

        <div class="form-group">
            <label for="destination">Destination:</label>
            <input type="text" class="form-control" id="destination" name="destination" value="<%= request.getParameter("destination") %>" required>
        </div>

        <div class="form-group">
            <label for="availability">Availability:</label>
            <input type="number" class="form-control" id="availability" name="availability" min="0" value="<%= request.getParameter("availability") %>" required>
        </div>

        <div class="form-group">
            <label for="activityType">Activity Type:</label>
            <input type="text" class="form-control" id="activityType" name="activityType" value="<%= request.getParameter("activityType") %>" required>
        </div>

        <button type="submit" class="btn btn-primary">Update Trip</button>
        <a href="admin_dashboard.jsp" class="btn btn-secondary">Back to Dashboard</a>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
