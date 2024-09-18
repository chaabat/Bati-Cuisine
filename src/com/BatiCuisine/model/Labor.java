package com.BatiCuisine.model;


import java.util.UUID;

public class Labor {
    private UUID componentId;   // Change componentId type to UUID
    private double hourlyRate;
    private double hoursWorked;
    private double productivityFactor;

    // Constructor with UUID (for labor fetched from the database)
    public Labor(UUID componentId, double hourlyRate, double hoursWorked, double productivityFactor) {
        this.componentId = componentId;
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.productivityFactor = productivityFactor;
    }

    // Constructor without UUID (for new labor)
    public Labor(double hourlyRate, double hoursWorked, double productivityFactor) {
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.productivityFactor = productivityFactor;
    }

    // Getters & Setters
    public UUID getComponentId() {
        return componentId;
    }

    public void setComponentId(UUID componentId) {
        this.componentId = componentId;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getProductivityFactor() {
        return productivityFactor;
    }

    public void setProductivityFactor(double productivityFactor) {
        this.productivityFactor = productivityFactor;
    }

    // Optionally override toString() for meaningful output
    @Override
    public String toString() {
        return "Labor{" +
                "componentId=" + componentId +
                ", hourlyRate=" + hourlyRate +
                ", hoursWorked=" + hoursWorked +
                ", productivityFactor=" + productivityFactor +
                '}';
    }
}
