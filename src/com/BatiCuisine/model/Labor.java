package com.BatiCuisine.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Labor {
    private UUID componentId; // UUID for database identification
    private String laborType; // String for type of labor
    private BigDecimal hourlyRate;
    private BigDecimal hoursWorked;
    private BigDecimal productivityFactor;

    // Constructor with UUID (for labor fetched from the database)
    public Labor(UUID componentId, BigDecimal hourlyRate, BigDecimal hoursWorked, BigDecimal productivityFactor) {
        this.componentId = componentId;
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.productivityFactor = productivityFactor;
    }

    // Constructor without UUID (for new labor)
    public Labor(String laborType, BigDecimal hourlyRate, BigDecimal hoursWorked, BigDecimal productivityFactor) {
        this(UUID.randomUUID(), hourlyRate, hoursWorked, productivityFactor);
    }

    public Labor(BigDecimal hourlyRate, BigDecimal hoursWorked, BigDecimal productivityFactor) {
    }

    // Getters & Setters
    public UUID getComponentId() {
        return componentId;
    }

    public void setComponentId(UUID componentId) {
        this.componentId = componentId;
    }

    public String getLaborType() {
        return laborType;
    }

    public void setLaborType(String laborType) {
        this.laborType = laborType;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public BigDecimal getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(BigDecimal hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public BigDecimal getProductivityFactor() {
        return productivityFactor;
    }

    public void setProductivityFactor(BigDecimal productivityFactor) {
        this.productivityFactor = productivityFactor;
    }

    @Override
    public String toString() {
        return "Labor{" +
                "componentId=" + componentId +
                ", laborType='" + laborType + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", hoursWorked=" + hoursWorked +
                ", productivityFactor=" + productivityFactor +
                '}';
    }
}
