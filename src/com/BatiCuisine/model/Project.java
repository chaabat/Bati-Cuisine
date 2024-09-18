package com.BatiCuisine.model;

import java.util.UUID;

public class Project {
    private UUID id;  // UUID for unique identification
    private String projectName;
    private double projectMargin;
    private double totalCost;
    private ProjectStatus projectStatus;  // Use enum for status
    private Client client;

    // Constructor for creating a new Project (without ID)
    public Project(String projectName, double projectMargin, ProjectStatus projectStatus, double totalCost, Client client) {
        this.id = UUID.randomUUID();  // Generate a new UUID for new projects
        this.projectName = projectName;
        this.projectMargin = projectMargin;
        this.projectStatus = projectStatus;
        this.totalCost = totalCost;
        this.client = client;
    }

    // Constructor for initializing a Project from the database (with ID)
    public Project(UUID id, String projectName, double projectMargin, ProjectStatus projectStatus, double totalCost, Client client) {
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

    public double getProjectMargin() {
        return projectMargin;
    }

    public void setProjectMargin(double projectMargin) {
        this.projectMargin = projectMargin;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
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
