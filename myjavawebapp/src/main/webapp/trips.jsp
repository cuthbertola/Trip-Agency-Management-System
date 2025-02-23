<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Search Trips</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
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

        table {
            width: 100%;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        .form-control {
            margin-bottom: 10px;
        }

        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
            transition: all 0.3s ease-in-out;
            font-weight: 600;
        }

        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #004085;
        }

        .btn-success {
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="dashboard-header">Search and Browse Trips</h2>

        <!-- Search Form -->
        <form action="searchTrips" method="get">
            <div class="form-group">
                <label for="destination">Destination:</label>
                <input type="text" class="form-control" name="destination" id="destination">
            </div>
            <div class="form-group">
                <label for="duration">Max Duration (Days):</label>
                <input type="number" class="form-control" name="duration" id="duration">
            </div>
            <div class="form-group">
                <label for="price">Max Price:</label>
                <input type="number" step="0.01" class="form-control" name="price" id="price">
            </div>
            <div class="form-group">
                <label for="activityType">Activity Type:</label>
                <input type="text" class="form-control" name="activityType" id="activityType">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </form>

        <hr>

        <!-- Trip Results Table -->
        <h2>Available Trips</h2>
        <div class="table-responsive">
            <table class="table table-bordered table-hover">
                <thead>
                    <tr>
                        <th>Trip Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Duration</th>
                        <th>Destination</th>
                        <th>Availability</th>
                        <th>Activity Type</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="trip" items="${trips}">
                        <tr>
                            <td>${trip.name}</td> <!-- Updated from tripName to name -->
                            <td>${trip.description}</td>
                            <td>$${trip.price}</td>
                            <td>${trip.duration} days</td>
                            <td>${trip.destination}</td>
                            <td>${trip.availability} seats</td>
                            <td>${trip.activityType}</td>
                            <td>
                                <form action="book" method="post">
                                    <input type="hidden" name="tripId" value="${trip.tripId}">
                                    <input type="number" name="participants" placeholder="Participants" required>
                                    <input type="hidden" name="userId" value="${sessionScope.userId}">
                                    <button type="submit" class="btn btn-success">Book Now</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty trips}">
                        <tr>
                            <td colspan="8" class="text-center text-secondary">No trips available.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
