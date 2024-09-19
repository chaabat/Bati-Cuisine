package com.BatiCuisine.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Material extends Component {
    private BigDecimal qualityCoefficient;
    private BigDecimal transportCost;

    // Constructor with UUID (for material fetched from the database)
    public Material(UUID componentId, String name, BigDecimal unitCost, BigDecimal quantity, BigDecimal taxRate, UUID projectId, BigDecimal qualityCoefficient, BigDecimal transportCost) {
        super(componentId, name, unitCost, quantity, taxRate, projectId);
        this.qualityCoefficient = qualityCoefficient;
        this.transportCost = transportCost;
    }

    // Constructor without UUID (for new material)
    public Material(String name, BigDecimal unitCost, BigDecimal quantity, BigDecimal taxRate, UUID projectId, BigDecimal qualityCoefficient, BigDecimal transportCost) {
        super(null, name, unitCost, quantity, taxRate, projectId);  // Pass null for UUID
        this.qualityCoefficient = qualityCoefficient;
        this.transportCost = transportCost;
    }

    // Getters & Setters
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

    @Override
    public String toString() {
        return "Material{" +
                "componentId=" + getComponentId() +
                ", name='" + getName() + '\'' +
                ", unitCost=" + getUnitCost() +
                ", quantity=" + getQuantity() +
                ", taxRate=" + getTaxRate() +
                ", projectId=" + getProjectId() +
                ", qualityCoefficient=" + qualityCoefficient +
                ", transportCost=" + transportCost +
                '}';
    }
}
