package com.BatiCuisine.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Component {
    private UUID componentId;
    private String name;
    private BigDecimal unitCost;
    private BigDecimal quantity;
    private BigDecimal taxRate;
    private UUID projectId;

    // Constructor
    public Component(UUID componentId, String name, BigDecimal unitCost, BigDecimal quantity, BigDecimal taxRate, UUID projectId) {
        this.componentId = componentId;
        this.name = name;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.taxRate = taxRate;
        this.projectId = projectId;
    }

    // Getters and Setters
    public UUID getComponentId() {
        return componentId;
    }

    public void setComponentId(UUID componentId) {
        this.componentId = componentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }
}
