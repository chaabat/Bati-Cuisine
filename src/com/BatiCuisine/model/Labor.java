package com.BatiCuisine.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Labor extends Component {
    private BigDecimal hourlyRate;
    private BigDecimal hoursWorked;
    private BigDecimal productivityFactor;

    // Constructor
    public Labor(UUID componentId, String name, BigDecimal unitCost, BigDecimal quantity, BigDecimal taxRate, UUID projectId, BigDecimal hourlyRate, BigDecimal hoursWorked, BigDecimal productivityFactor) {
        super(componentId, name, unitCost, quantity, taxRate,  projectId);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.productivityFactor = productivityFactor;
    }

    // Getters and Setters
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
}
