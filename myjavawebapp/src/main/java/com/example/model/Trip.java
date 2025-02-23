package com.example.model;

import java.math.BigDecimal;

public class Trip {
    private int tripId;
    private String name; // Renamed to match JSP property usage
    private String description;
    private BigDecimal price; // Change from double to BigDecimal
    private int duration;
    private String destination;
    private int availability;
    private String activityType;

    // Default Constructor
    public Trip() {}

    // Constructor for easier initialization
    public Trip(int tripId, String name, String description, BigDecimal price, int duration, String destination, int availability, String activityType) {
        this.tripId = tripId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.destination = destination;
        this.availability = availability;
        this.activityType = activityType;
    }

    // Getter and Setter for tripId
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for price
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    // Getter and Setter for duration
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
        this.duration = duration;
    }

    // Getter and Setter for destination
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    // Getter and Setter for availability
    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        if (availability < 0) {
            throw new IllegalArgumentException("Availability cannot be negative");
        }
        this.availability = availability;
    }

    // Getter and Setter for activityType
    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    // toString method for easier debugging
    @Override
    public String toString() {
        return "Trip{" +
                "tripId=" + tripId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", destination='" + destination + '\'' +
                ", availability=" + availability +
                ", activityType='" + activityType + '\'' +
                '}';
    }
}
