package com.BatiCuisine.model;

public class Labor {
    private int componentId;
    private double hourlyRate;
    private double hoursWorked;
    private double productivityFactor;

    // Constructor
    public Labor(int componentId, double hourlyRate, double hoursWorked, double productivityFactor) {
        this.componentId = componentId;
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.productivityFactor = productivityFactor;
    }

    // Getters & Setters
    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
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
