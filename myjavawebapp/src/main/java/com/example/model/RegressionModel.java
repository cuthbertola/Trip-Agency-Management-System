package com.example.model;

import weka.classifiers.trees.M5P;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class RegressionModel {
    private final M5P model;
    private final Instances data;

    public RegressionModel(String arffFilePath) throws Exception {
        try {
            // Load the dataset
            DataSource source = new DataSource(arffFilePath);
            data = source.getDataSet();

            // Set the index of the attribute to predict (Revenue)
            data.setClassIndex(data.numAttributes() - 1);

            // Train the Decision Tree Regression model (M5P)
            model = new M5P();
            model.buildClassifier(data);

            // Log the regression model summary for debugging
            System.out.println("Decision Tree Model Summary: \n" + model.toString());
        } catch (Exception e) {
            System.err.println("Error initializing the RegressionModel: " + e.getMessage());
            throw new Exception("Failed to initialize RegressionModel", e);
        }
    }

    public double predict(double advertisingCost, String month, int year) throws Exception {
        try {
            // Validate inputs to ensure they are within the range of the dataset
            validateInputs(advertisingCost, month, year);

            // Convert month to sine and cosine values
            double monthSin = Math.sin(2 * Math.PI * convertMonthToIndex(month) / 12.0);
            double monthCos = Math.cos(2 * Math.PI * convertMonthToIndex(month) / 12.0);

            // Create an instance for prediction
            DenseInstance instance = new DenseInstance(data.numAttributes());
            instance.setValue(data.attribute("Year"), year);
            instance.setValue(data.attribute("AdvertisingCost"), advertisingCost);
            instance.setValue(data.attribute("Month_Sin"), monthSin);
            instance.setValue(data.attribute("Month_Cos"), monthCos);
            instance.setMissing(data.attribute("Revenue"));

            // Associate the instance with the dataset
            instance.setDataset(data);

            // Log the instance details for debugging
            System.out.println("Predicting for Instance: " + instance.toString());

            // Make the prediction
            double predictedValue = model.classifyInstance(instance);

            // Log the predicted value
            System.out.println("Predicted Revenue: £" + predictedValue);
            return predictedValue;

        } catch (Exception e) {
            System.err.println("Error during prediction: " + e.getMessage());
            throw new Exception("Prediction failed", e);
        }
    }

    public double predictNextYear(double advertisingCost, String month, int year) throws Exception {
        try {
            // Increment the year by 1
            int nextYear = year + 1;

            // Log predicting next year with the original advertising cost
            System.out.println("Predicting revenue for next year with Advertising Cost: £" + advertisingCost + " and Month: " + month + " Year: " + nextYear);

            // Predict revenue for next year
            return predict(advertisingCost, month, nextYear);
        } catch (Exception e) {
            System.err.println("Error during next year prediction: " + e.getMessage());
            throw new Exception("Next year prediction failed", e);
        }
    }

    private void validateInputs(double advertisingCost, String month, int year) throws IllegalArgumentException {
        if (!isValidMonth(month)) {
            throw new IllegalArgumentException("Invalid month value: " + month);
        }

        if (advertisingCost < 0) {
            throw new IllegalArgumentException("Advertising cost must be a positive value.");
        }

        if (year < 0) {
            throw new IllegalArgumentException("Year must be a positive value.");
        }
    }

    private boolean isValidMonth(String month) {
        switch (month.toLowerCase()) {
            case "jan":
            case "feb":
            case "mar":
            case "apr":
            case "may":
            case "jun":
            case "jul":
            case "aug":
            case "sep":
            case "oct":
            case "nov":
            case "dec":
                return true;
            default:
                return false;
        }
    }

    private int convertMonthToIndex(String month) {
        switch (month.toLowerCase()) {
            case "jan": return 0;
            case "feb": return 1;
            case "mar": return 2;
            case "apr": return 3;
            case "may": return 4;
            case "jun": return 5;
            case "jul": return 6;
            case "aug": return 7;
            case "sep": return 8;
            case "oct": return 9;
            case "nov": return 10;
            case "dec": return 11;
            default: throw new IllegalArgumentException("Invalid month value: " + month);
        }
    }
}
