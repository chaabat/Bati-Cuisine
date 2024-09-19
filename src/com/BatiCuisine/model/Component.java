package com.BatiCuisine.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Component {
    private UUID id;
    private String name;
    private BigDecimal unitCost;
    private BigDecimal quantity;
    private BigDecimal taxRate;
    private UUID projectId;

    // Constructor
    public Component(UUID componentId, String name, BigDecimal unitCost, BigDecimal quantity, BigDecimal taxRate, UUID projectId) {
        this.id = componentId;  // Assigning the parameter to the instance variable
        this.name = name;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.taxRate = taxRate;
        this.projectId = projectId;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
