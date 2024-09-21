package com.BatiCuisine.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Labor extends Component {
    private BigDecimal hourlyRate;
    private BigDecimal hoursWorked;
    private BigDecimal productivityFactor;

    // Constructor
    public Labor(UUID componentId, String name, BigDecimal unitCost, BigDecimal quantity, BigDecimal taxRate, UUID projectId, BigDecimal hourlyRate, BigDecimal hoursWorked, BigDecimal productivityFactor) {
        super(componentId, name, unitCost, quantity, taxRate, projectId);
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

    // Override toString() to print labor details in a readable format
    @Override
    public String toString() {
        return String.format("Labor: %s | Hours Worked: %.2f | Hourly Rate: %.2f | Tax: %.2f | Productivity: %.2f",
                getName(), hoursWorked, hourlyRate, getTaxRate(), productivityFactor);
    }

    // Optional: Override equals and hashCode if you need to compare Labor objects or store them in collections like HashSet
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Labor)) return false;
        if (!super.equals(o)) return false;

        Labor labor = (Labor) o;

        if (!hourlyRate.equals(labor.hourlyRate)) return false;
        if (!hoursWorked.equals(labor.hoursWorked)) return false;
        return productivityFactor.equals(labor.productivityFactor);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + hourlyRate.hashCode();
        result = 31 * result + hoursWorked.hashCode();
        result = 31 * result + productivityFactor.hashCode();
        return result;
    }
}
