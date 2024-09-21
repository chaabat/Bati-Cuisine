package com.BatiCuisine.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Project {
    private UUID id;                     // Project ID
    private String projectName;          // Name of the project
    private BigDecimal projectMargin;    // Profit margin
    private BigDecimal totalCost;        // Total cost of the project
    private ProjectStatus projectStatus; // Current status of the project
    private Client client;               // Associated client
    private List<Material> materials;    // List of materials
    private List<Labor> labors;          // List of labor
    private BigDecimal surface;          // Surface area (if applicable)
    private String type;                 // Type of the project (e.g., Renovation, New Construction)

    // Constructor for initializing a Project from the database (with ID)
    public Project(UUID id, String projectName, BigDecimal projectMargin, ProjectStatus projectStatus, BigDecimal totalCost, Client client, BigDecimal surface, String type) {
        this.id = id;
        this.projectName = projectName;
        this.projectMargin = projectMargin;
        this.projectStatus = projectStatus;
        this.totalCost = totalCost;
        this.client = client;
        this.materials = new ArrayList<>(); // Initialize empty lists
        this.labors = new ArrayList<>();
        this.surface = surface;
        this.type = type;
    }

    // Constructor for creating a new Project (without ID)
    public Project(String projectName, BigDecimal projectMargin, ProjectStatus projectStatus, BigDecimal totalCost, Client client, BigDecimal surface, String type) {
        this(UUID.randomUUID(), projectName, projectMargin, projectStatus, totalCost, client, surface, type);
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

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<Labor> getLabors() {
        return labors;
    }

    public void setLabors(List<Labor> labors) {
        this.labors = labors;
    }

    public BigDecimal getSurface() {
        return surface;
    }

    public void setSurface(BigDecimal surface) {
        this.surface = surface;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Add a material to the project
    public void addMaterial(Material material) {
        this.materials.add(material);
    }

    // Add labor to the project
    public void addLabor(Labor labor) {
        this.labors.add(labor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
                Objects.equals(projectName, project.projectName) &&
                Objects.equals(projectMargin, project.projectMargin) &&
                Objects.equals(totalCost, project.totalCost) &&
                projectStatus == project.projectStatus &&
                Objects.equals(client, project.client) &&
                Objects.equals(materials, project.materials) &&
                Objects.equals(labors, project.labors) &&
                Objects.equals(surface, project.surface) &&
                Objects.equals(type, project.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectName, projectMargin, totalCost, projectStatus, client, materials, labors, surface, type);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Project ID: ").append(id).append("\n");
        builder.append("Project Name: ").append(projectName).append("\n");
        builder.append("Client: ").append(client.getName()).append("\n");
        builder.append("Client Address: ").append(client.getAddress()).append("\n");
        builder.append("Project Margin: ").append(projectMargin).append("%\n");
        builder.append("Total Cost: ").append(totalCost).append("€\n");
        builder.append("Status: ").append(projectStatus).append("\n");
        builder.append("Surface: ").append(surface).append(" m²\n");
        builder.append("Type: ").append(type).append("\n");

        // Append material details
        builder.append("\nMaterials:\n");
        if (!materials.isEmpty()) {
            for (Material material : materials) {
                builder.append(material.toString()).append("\n");
            }
        } else {
            builder.append("No materials added.\n"); // If materials list is empty
        }

        // Append labor details
        builder.append("\nLabors:\n");
        if (!labors.isEmpty()) {
            for (Labor labor : labors) {
                builder.append(labor.toString()).append("\n");
            }
        } else {
            builder.append("No labor added.\n"); // If labors list is empty
        }

        return builder.toString();
    }

}
