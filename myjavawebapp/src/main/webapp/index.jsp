<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to Trip Agency Management System</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body {
            background: linear-gradient(to bottom right, #6dd5ed, #2193b0);
            font-family: 'Arial', sans-serif;
            color: #ffffff;
        }
        .hero-section {
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
        }
        .hero-content {
            background: rgba(0, 0, 0, 0.6);
            padding: 50px;
            border-radius: 10px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.6);
        }
        .hero-title {
            font-size: 3rem;
            font-weight: bold;
        }
        .hero-description {
            font-size: 1.25rem;
            margin-bottom: 30px;
        }
        .btn-custom {
            padding: 10px 30px;
            font-size: 1.1rem;
            font-weight: bold;
            border-radius: 5px;
            margin-bottom: 10px;
        }
        .btn-primary-custom {
            background-color: #ff8c00;
            border: none;
        }
        .btn-primary-custom:hover {
            background-color: #ff7043;
        }
        .btn-outline-light-custom {
            border: 2px solid #fff;
        }
        .btn-outline-light-custom:hover {
            background-color: #fff;
            color: #333;
        }
        .btn-admin-custom {
            background-color: #28a745;
            border: none;
        }
        .btn-admin-custom:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="hero-section">
        <div class="hero-content">
            <h1 class="hero-title">Welcome to Trip Agency Management System</h1>
            <p class="hero-description">Effortlessly manage your travel agency's trips and bookings, and explore our rich collection of travel options.</p>
            <div>
                <!-- User Login Button -->
                <a href="login.jsp" class="btn btn-primary-custom btn-custom text-white me-3"><i class="fas fa-sign-in-alt"></i> Login to Your Account</a>
                
                <!-- Admin Login Button -->
                <a href="admin_login.jsp" class="btn btn-admin-custom btn-custom text-white me-3"><i class="fas fa-user-shield"></i> Login as an Administrator</a>

                <!-- Learn More Button -->
                <a href="#" class="btn btn-outline-light-custom btn-custom"><i class="fas fa-info-circle"></i> Learn More</a>
            </div>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
</body>
</html>
