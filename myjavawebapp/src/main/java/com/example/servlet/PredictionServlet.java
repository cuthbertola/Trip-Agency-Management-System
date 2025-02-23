package com.example.servlet;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.model.RegressionModel;

@WebServlet("/predict")
public class PredictionServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(PredictionServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Retrieve user inputs from the form
            String advertisingCostStr = request.getParameter("advertisingCost");
            String month = request.getParameter("month");
            String yearStr = request.getParameter("year");

            LOGGER.log(Level.INFO, "Received inputs - Advertising Cost: {0}, Month: {1}, Year: {2}", new Object[]{advertisingCostStr, month, yearStr});

            // Validate inputs
            if (advertisingCostStr == null || advertisingCostStr.isEmpty() || month == null || month.isEmpty() || yearStr == null || yearStr.isEmpty()) {
                LOGGER.log(Level.WARNING, "Missing input data for prediction.");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Advertising cost, month, and year are required.");
                return;
            }

            // Parse input values
            double advertisingCost;
            int year;
            try {
                advertisingCost = Double.parseDouble(advertisingCostStr);
                year = Integer.parseInt(yearStr);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Invalid input: Advertising Cost: {0}, Year: {1}", new Object[]{advertisingCostStr, yearStr});
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input for advertising cost or year.");
                return;
            }

            // Get the path to the ARFF file
            String arffFilePath = getServletContext().getRealPath("/WEB-INF/sales_prediction.arff");
            LOGGER.log(Level.INFO, "ARFF file path: {0}", arffFilePath);

            // Verify the ARFF file exists
            File arffFile = new File(arffFilePath);
            if (!arffFile.exists()) {
                LOGGER.log(Level.SEVERE, "ARFF file not found at: {0}", arffFilePath);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ARFF file not found.");
                return;
            }

            // Use the RegressionModel to make predictions
            RegressionModel model = new RegressionModel(arffFilePath);

            // Predict revenue with the original advertising cost
            double predictedRevenue = model.predict(advertisingCost, month, year);

            // Predict revenue with a 10% increase in advertising cost
            double incrementPercentage = 10.0;
            double increasedAdvertisingCost = advertisingCost * (1 + incrementPercentage / 100);
            double increasedRevenuePrediction = model.predict(increasedAdvertisingCost, month, year);

            // Predict revenue for next year with the original advertising cost
            double predictedRevenueNextYear = model.predictNextYear(advertisingCost, month, year);

            // Predict revenue for next year with a 10% increase in advertising cost
            double increasedAdvertisingCostNextYear = advertisingCost * (1 + incrementPercentage / 100);
            double increasedRevenuePredictionNextYear = model.predictNextYear(increasedAdvertisingCostNextYear, month, year);

            LOGGER.log(Level.INFO, "Original Revenue: {0}, Increased Revenue: {1}, Next Year Revenue: {2}, Increased Next Year Revenue: {3}",
                    new Object[]{predictedRevenue, increasedRevenuePrediction, predictedRevenueNextYear, increasedRevenuePredictionNextYear});

            // Format currency values
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);

            // Pass the results to the JSP page
            request.setAttribute("predictedRevenue", currencyFormat.format(predictedRevenue));
            request.setAttribute("increasedAdvertisingCost", currencyFormat.format(increasedAdvertisingCost));
            request.setAttribute("increasedRevenuePrediction", currencyFormat.format(increasedRevenuePrediction));
            request.setAttribute("predictedRevenueNextYear", currencyFormat.format(predictedRevenueNextYear));
            request.setAttribute("increasedAdvertisingCostNextYear", currencyFormat.format(increasedAdvertisingCostNextYear));
            request.setAttribute("increasedRevenuePredictionNextYear", currencyFormat.format(increasedRevenuePredictionNextYear));

            // Use an absolute path to ensure the JSP file is found correctly
            String path = "/prediction_result.jsp";
            LOGGER.log(Level.INFO, "Forwarding to JSP: {0}", path);
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            dispatcher.forward(request, response);

        } catch (IOException | ServletException e) {
            LOGGER.log(Level.SEVERE, "Error during prediction processing: {0}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing the prediction.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error: {0}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error occurred.");
        }
    }
}
