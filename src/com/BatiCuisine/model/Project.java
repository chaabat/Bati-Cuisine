package com.BatiCuisine.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Project {
    private UUID id;
    private String projectName;
    private BigDecimal projectMargin;  // Use BigDecimal for precision
    private BigDecimal totalCost;      // Use BigDecimal for precision
    private ProjectStatus projectStatus;
    private Client client;

    // Constructor for creating a new Project (without ID)
    public Project(String projectName, BigDecimal projectMargin, ProjectStatus projectStatus, BigDecimal totalCost, Client client) {
        this.id = UUID.randomUUID();
        this.projectName = projectName;
        this.projectMargin = projectMargin;
        this.projectStatus = projectStatus;
        this.totalCost = totalCost;
        this.client = client;
    }

    // Constructor for initializing a Project from the database (with ID)
    public Project(UUID id, String projectName, BigDecimal projectMargin, ProjectStatus projectStatus, BigDecimal totalCost, Client client) {
        this.id = id;
        this.projectName = projectName;
        this.projectMargin = projectMargin;
        this.projectStatus = projectStatus;
        this.totalCost = totalCost;
        this.client = client;
    }

    // Getters & Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getProjectMargin() {
        return projectMargin;
    }

    public void setProjectMargin(BigDecimal projectMargin) {
        this.projectMargin = projectMargin;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
