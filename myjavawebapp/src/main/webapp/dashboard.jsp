<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(to bottom, #f0f4f8, #d9e8f5);
            font-family: 'Poppins', sans-serif;
        }
        .dashboard-container {
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
        .btn-action {
            padding: 5px 15px;
            font-size: 0.9rem;
        }
        .dashboard-header {
            font-weight: 600;
            color: #333;
        }
        .search-form {
            margin-bottom: 30px;
        }
        .dashboard-title {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .btn-add {
            background-color: #28a745;
            border-color: #28a745;
            transition: all 0.3s ease-in-out;
            font-weight: 600;
        }
        .btn-add:hover {
            background-color: #218838;
            border-color: #1e7e34;
        }
    </style>
</head>
<body>
    <div class="container dashboard-container">
        <div class="dashboard-title">
            <h2 class="dashboard-header">Welcome to the Trip Dashboard</h2>
            <a href="${pageContext.request.contextPath}/add_trip.jsp" class="btn btn-add">Add New Trip</a>
        </div>

        <!-- Search Form -->
        <form class="search-form" method="get" action="${pageContext.request.contextPath}/searchTrips">
            <div class="row">
                <div class="col-md-3 mb-3">
                    <input type="text" class="form-control" placeholder="Search by Destination" name="destination" value="${param.destination}">
                </div>
                <div class="col-md-3 mb-3">
                    <input type="text" class="form-control" placeholder="Search by Activity Type" name="activityType" value="${param.activityType}">
                </div>
                <div class="col-md-3 mb-3">
                    <input type="number" class="form-control" placeholder="Max Price" name="maxPrice" min="0" value="${param.maxPrice}">
                </div>
                <div class="col-md-3 mb-3">
                    <input type="number" class="form-control" placeholder="Duration (Days)" name="duration" min="1" value="${param.duration}">
                </div>
                <div class="col-md-3 mb-3">
                    <button type="submit" class="btn btn-primary w-100">Search</button>
                </div>
            </div>
        </form>

        <!-- Trip Table -->
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
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="trip" items="${trips}">
                        <tr>
                            <td>${trip.name}</td>
                            <td>${trip.description}</td>
                            <td>$${trip.price}</td>
                            <td>${trip.duration}</td>
                            <td>${trip.destination}</td>
                            <td>${trip.availability} seats left</td>
                            <td>
                                <!-- View Button to see detailed trip information -->
                                <a href="${pageContext.request.contextPath}/tripDetails?tripId=${trip.tripId}" class="btn btn-info btn-action mb-2">View</a>

                                <!-- Booking Form -->
                                <form action="${pageContext.request.contextPath}/book" method="post">
                                    <input type="hidden" name="tripId" value="${trip.tripId}">
                                    <input type="hidden" name="userId" value="${sessionScope.userId}">
                                    
                                    <!-- Trip Date Field -->
                                    <div class="mb-2">
                                        <label for="tripDate-${trip.tripId}" class="form-label">Trip Date</label>
                                        <input type="date" id="tripDate-${trip.tripId}" name="tripDate" class="form-control" required>
                                    </div>
                                    
                                    <!-- Participants Field -->
                                    <div class="mb-2">
                                        <label for="participants-${trip.tripId}" class="form-label">Participants</label>
                                        <input type="number" id="participants-${trip.tripId}" name="participants" class="form-control" min="1" max="${trip.availability}" required>
                                    </div>
                                    
                                    <!-- User Name Field -->
                                    <div class="mb-2">
                                        <label for="userName-${trip.tripId}" class="form-label">Your Name</label>
                                        <input type="text" id="userName-${trip.tripId}" name="userName" class="form-control" value="${sessionScope.userName}" required>
                                    </div>
                                    
                                    <!-- User Email Field -->
                                    <div class="mb-2">
                                        <label for="userEmail-${trip.tripId}" class="form-label">Your Email</label>
                                        <input type="email" id="userEmail-${trip.tripId}" name="userEmail" class="form-control" value="${sessionScope.userEmail}" required>
                                    </div>
                                    
                                    <!-- Submit Button -->
                                    <button type="submit" class="btn btn-success btn-action">Book Now</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty trips}">
                        <tr>
                            <td colspan="7" class="text-center text-secondary">No trips available.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
