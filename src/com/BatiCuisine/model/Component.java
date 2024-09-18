package com.BatiCuisine.model;


import java.util.UUID;

public class Component {
    private UUID id;
    private String name;
    private double unitCost;
    private double quantity;
    private String componentType;
    private double taxRate;
    private Project project;

    // Constructor with UUID (for components fetched from the database)
    public Component(UUID id, String name, double unitCost, double quantity, String componentType, double taxRate, Project project) {
        this.id = id;
        this.name = name;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.componentType = componentType;
        this.taxRate = taxRate;
        this.project = project;
    }

    // Constructor without UUID (for new components, UUID generated in database)
    public Component(String name, double unitCost, double quantity, String componentType, double taxRate, Project project) {
        this.name = name;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.componentType = componentType;
        this.taxRate = taxRate;
        this.project = project;
    }

    // Getters & Setters
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

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
