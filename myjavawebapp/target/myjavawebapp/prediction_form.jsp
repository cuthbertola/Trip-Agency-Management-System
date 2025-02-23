<%@ include file="header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Predict Revenue</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow-lg">
                    <div class="card-header bg-primary text-white text-center">
                        <h3>Predict Revenue</h3>
                    </div>
                    <div class="card-body">
                        <p class="text-muted text-center mb-4">
                            Enter your <strong>advertising cost</strong>, select a <strong>month</strong>, and provide the <strong>year</strong> to predict the revenue for your business.
                        </p>
                        <form action="${pageContext.request.contextPath}/predict" method="post" accept-charset="UTF-8">
                            <div class="mb-3">
                                <label for="advertisingCost" class="form-label">Advertising Cost (£):</label>
                                <input 
                                    type="number" 
                                    step="0.01" 
                                    id="advertisingCost" 
                                    name="advertisingCost" 
                                    class="form-control" 
                                    placeholder="Enter advertising cost (e.g., 1200.00)" 
                                    required>
                                <small class="text-muted">Enter a positive value in pounds (e.g., 1000.00).</small>
                            </div>
                            <div class="mb-3">
                                <label for="month" class="form-label">Select Month:</label>
                                <select id="month" name="month" class="form-select" required>
                                    <option value="" disabled selected>Choose a month</option>
                                    <option value="Jan">January</option>
                                    <option value="Feb">February</option>
                                    <option value="Mar">March</option>
                                    <option value="Apr">April</option>
                                    <option value="May">May</option>
                                    <option value="Jun">June</option>
                                    <option value="Jul">July</option>
                                    <option value="Aug">August</option>
                                    <option value="Sep">September</option>
                                    <option value="Oct">October</option>
                                    <option value="Nov">November</option>
                                    <option value="Dec">December</option>
                                </select>
                                <small class="text-muted">Select the month corresponding to the predicted revenue.</small>
                            </div>
                            <div class="mb-3">
                                <label for="year" class="form-label">Year:</label>
                                <input 
                                    type="number" 
                                    id="year" 
                                    name="year" 
                                    class="form-control" 
                                    placeholder="Enter the year (e.g., 2024)" 
                                    required>
                                <small class="text-muted">Enter the year for which the data is being provided (e.g., 2024).</small>
                            </div>
                            <div class="text-center">
                                <button type="submit" class="btn btn-primary px-4">Predict</button>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer text-center text-muted">
                        <small>Prediction powered by <strong>Weka</strong> and <strong>Java</strong>.</small>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
