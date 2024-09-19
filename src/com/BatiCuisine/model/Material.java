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
}
