package com.BatiCuisine.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Material extends Component {
    private BigDecimal qualityCoefficient;
    private BigDecimal transportCost;

    // Constructor
    public Material(UUID componentId, String name, BigDecimal unitCost, BigDecimal quantity, BigDecimal taxRate, UUID projectId, BigDecimal qualityCoefficient, BigDecimal transportCost) {
        super(componentId, name, unitCost, quantity, taxRate, projectId);
        this.qualityCoefficient = qualityCoefficient;
        this.transportCost = transportCost;
    }

    // Getters and Setters
    public BigDecimal getQualityCoefficient() {
        return qualityCoefficient;
    }

    public void setQualityCoefficient(BigDecimal qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }

    public BigDecimal getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(BigDecimal transportCost) {
        this.transportCost = transportCost;
    }

    // Override toString() to print material details in a readable format
    @Override
    public String toString() {
        return String.format("Material: %s | Quantity: %.2f | Unit Cost: %.2f | Tax: %.2f | Transport Cost: %.2f | Quality Coefficient: %.2f",
                getName(), getQuantity(), getUnitCost(), getTaxRate(), transportCost, qualityCoefficient);
    }

    // Optional: Override equals and hashCode if you need to compare Material objects or store them in collections like HashSet
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Material)) return false;
        if (!super.equals(o)) return false;

        Material material = (Material) o;

        if (!qualityCoefficient.equals(material.qualityCoefficient)) return false;
        return transportCost.equals(material.transportCost);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + qualityCoefficient.hashCode();
        result = 31 * result + transportCost.hashCode();
        return result;
    }
}
