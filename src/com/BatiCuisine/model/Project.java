package com.BatiCuisine.model;

import com.BatiCuisine.util.CostCalculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Project {
    private UUID id;
    private String projectName;
    private BigDecimal projectMargin;
    private BigDecimal totalCost;
    private ProjectStatus projectStatus;
    private Client client;
    private List<Material> materials;
    private List<Labor> labors;
    private BigDecimal surface;
    private String type;

    // Initializing a Project from the database (with ID)
    public Project(UUID id, String projectName, BigDecimal projectMargin, ProjectStatus projectStatus, BigDecimal totalCost, Client client, BigDecimal surface, String type) {
        this.id = id;
        this.projectName = projectName;
        this.projectMargin = projectMargin;
        this.projectStatus = projectStatus;
        this.totalCost = totalCost;
        this.client = client;
        this.materials = new ArrayList<>();
        this.labors = new ArrayList<>();
        this.surface = surface;
        this.type = type;
    }

    // Creating a new Project without ID
    public Project(String projectName, BigDecimal projectMargin, ProjectStatus projectStatus, BigDecimal totalCost, Client client, BigDecimal surface, String type) {
        this(UUID.randomUUID(), projectName, projectMargin, projectStatus, totalCost, client, surface, type);
    }

    // Getters & Setters
    public List<Material> getMaterials() {
        return materials;
    }

    public List<Labor> getLabors() {
        return labors;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
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

    public BigDecimal getSurface() {
        return surface;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return projectStatus != null ? projectStatus.name() : "Unknown status";
    }

    // Calculate total costs for materials and labor
    public BigDecimal calculateTotalCost() {
        BigDecimal totalMaterialCost = materials.stream()
                .map(Material::getTotalCost) // Assuming Material has a method to get its total cost
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalLaborCost = labors.stream()
                .map(Labor::getTotalCost) // Assuming Labor has a method to get its total cost
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalMaterialCost.add(totalLaborCost);
    }

    public BigDecimal calculateFinalCost() {
        BigDecimal totalCostBeforeMargin = calculateTotalCost();
        BigDecimal profitMargin = totalCostBeforeMargin.multiply(new BigDecimal("0.15")); // 15% margin
        return totalCostBeforeMargin.add(profitMargin);
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
        builder.append("Total Cost: ").append(calculateFinalCost()).append("€\n"); // Use final cost here
        builder.append("Status: ").append(projectStatus).append("\n");
        builder.append("Surface: ").append(surface).append(" m²\n");
        builder.append("Type: ").append(type).append("\n");

        // Material details
        builder.append("\nMaterials:\n");
        if (!materials.isEmpty()) {
            for (Material material : materials) {
                builder.append(material.toString()).append("\n");
            }
        } else {
            builder.append("No materials added.\n");
        }

        // Labor details
        builder.append("\nLabors:\n");
        if (!labors.isEmpty()) {
            for (Labor labor : labors) {
                builder.append(labor.toString()).append("\n");
            }
        } else {
            builder.append("No labor added.\n");
        }

        return builder.toString();
    }
}
