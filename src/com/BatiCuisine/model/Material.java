package com.BatiCuisine.model;

public class Material {
    private int componentId;
    private double qualityCoefficient;
    private double transportCost;

    // Constructor

    public Material(int componentId, double qualityCoefficient, double transportCost) {
        this.componentId = componentId;
        this.qualityCoefficient = qualityCoefficient;
        this.transportCost = transportCost;
    }

    // Getters & Setters

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
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
