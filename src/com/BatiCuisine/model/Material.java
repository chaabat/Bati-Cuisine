package com.BatiCuisine.model;

import java.util.UUID;

public class Material {
    private UUID componentId;   // Change componentId type to UUID
    private double qualityCoefficient;
    private double transportCost;

    // Constructor with UUID (for material fetched from the database)
    public Material(UUID componentId, double qualityCoefficient, double transportCost) {
        this.componentId = componentId;
        this.qualityCoefficient = qualityCoefficient;
        this.transportCost = transportCost;
    }

    // Constructor without UUID (for new material)
    public Material(double qualityCoefficient, double transportCost) {
        this.qualityCoefficient = qualityCoefficient;
        this.transportCost = transportCost;
    }

    // Getters & Setters

    public UUID getComponentId() {
        return componentId;
    }

    public void setComponentId(UUID componentId) {
        this.componentId = componentId;
    }

    public double getQualityCoefficient() {
        return qualityCoefficient;
    }

    public void setQualityCoefficient(double qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }

    public double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(double transportCost) {
        this.transportCost = transportCost;
    }

    @Override
    public String toString() {
        return "Material{" +
                "componentId=" + componentId +
                ", qualityCoefficient=" + qualityCoefficient +
                ", transportCost=" + transportCost +
                '}';
    }
}
