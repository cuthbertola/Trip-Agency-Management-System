<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prediction Result</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow-lg">
                    <div class="card-header bg-primary text-white text-center">
                        <h3>Prediction Result</h3>
                    </div>
                    <div class="card-body">
                        <!-- Original Prediction -->
                        <p class="lead text-center">
                            <strong>The predicted revenue is:</strong>
                            <span class="text-success fs-4">
                                <%= request.getAttribute("predictedRevenue") %>
                            </span>
                        </p>
                        
                        <!-- Prediction with 10% Increase -->
                        <p class="lead text-center">
                            <strong>With a 10% increase in advertising cost (<%= request.getAttribute("increasedAdvertisingCost") %>):</strong>
                            <br>
                            <span class="text-info fs-4">
                                Predicted Revenue: <%= request.getAttribute("increasedRevenuePrediction") %>
                            </span>
                        </p>

                        <!-- Prediction for Next Year -->
                        <p class="lead text-center">
                            <strong>The predicted revenue for next year is:</strong>
                            <span class="text-primary fs-4">
                                <%= request.getAttribute("predictedRevenueNextYear") %>
                            </span>
                        </p>

                        <!-- Prediction for Next Year with 10% Increase -->
                        <p class="lead text-center">
                            <strong>With a 10% increase in advertising cost for next year (<%= request.getAttribute("increasedAdvertisingCostNextYear") %>):</strong>
                            <br>
                            <span class="text-warning fs-4">
                                Predicted Revenue for Next Year: <%= request.getAttribute("increasedRevenuePredictionNextYear") %>
                            </span>
                        </p>
                    </div>
                    <div class="card-footer text-center">
                        <a href="${pageContext.request.contextPath}/prediction_form.jsp" class="btn btn-outline-primary me-2">Make Another Prediction</a>
                        <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-outline-secondary">Return to Dashboard</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
