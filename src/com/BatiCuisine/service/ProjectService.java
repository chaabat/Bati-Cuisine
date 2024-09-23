package com.BatiCuisine.service;

import com.BatiCuisine.model.Project;
import com.BatiCuisine.model.Material;
import com.BatiCuisine.model.Labor;
import com.BatiCuisine.model.ProjectStatus;
import com.BatiCuisine.repository.interfaces.ProjectRepository;
import com.BatiCuisine.util.CostCalculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MaterialService materialService;
    private final LaborService laborService;

    public ProjectService(ProjectRepository projectRepository, MaterialService materialService, LaborService laborService) {
        this.projectRepository = projectRepository;
        this.materialService = materialService;
        this.laborService = laborService;
    }

    public void addProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        projectRepository.addProject(project);
    }

    public Optional<Project> getProjectById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
        return projectRepository.getProjectById(id);
    }

    public List<Project> listAllProjects() {
        return Optional.ofNullable(projectRepository.getAllProjects()).orElse(new ArrayList<>());
    }

    public Optional<Project> calculateProjectCost(Project project, BigDecimal vatRate, BigDecimal profitMargin) {
        validateRate(vatRate);
        validateRate(profitMargin);

        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        // Set profit margin if it's provided during cost calculation
        if (profitMargin != null) {
            project.setProjectMargin(profitMargin);
        }

        List<Material> materials = materialService.findByProjectId(project.getId());
        List<Labor> labors = laborService.findByProjectId(project.getId());

        // Calculate costs
        BigDecimal totalMaterialCost = CostCalculator.calculateTotalMaterialCost(materials);
        BigDecimal totalLaborCost = CostCalculator.calculateTotalLaborCost(labors);

        // Compute total project cost
        BigDecimal totalProjectCost = CostCalculator.calculateTotalProjectCost(
                totalMaterialCost, totalLaborCost, vatRate, project.getProjectMargin()
        );

        project.setTotalCost(totalProjectCost);  // Update total cost in the project

        // Save the updated project in the repository
        updateProject(project);

        return Optional.of(project);
    }


    private void validateRate(BigDecimal rate) {
        if (rate == null || rate.compareTo(BigDecimal.ZERO) < 0 || rate.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Rate must be between 0 and 100");
        }
    }

    private BigDecimal calculateVat(BigDecimal totalCost, BigDecimal vatRate) {
        return vatRate.multiply(totalCost).divide(BigDecimal.valueOf(100));
    }

    private BigDecimal calculateProfit(BigDecimal totalCost, BigDecimal profitMargin) {
        if (totalCost == null) {
            throw new IllegalArgumentException("Total cost cannot be null");
        }
        if (profitMargin == null) {
            return BigDecimal.ZERO;  // Return 0 if profit margin is not provided
        }
        return profitMargin.multiply(totalCost).divide(BigDecimal.valueOf(100));
    }


    public void displayProjectCostDetails(Project project, BigDecimal vatRate) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        List<Material> materials = Optional.ofNullable(materialService.findByProjectId(project.getId())).orElse(new ArrayList<>());
        List<Labor> labors = Optional.ofNullable(laborService.findByProjectId(project.getId())).orElse(new ArrayList<>());

        BigDecimal totalMaterialCost = materialService.calculateTotalMaterialCost(materials);
        BigDecimal totalLaborCost = laborService.calculateTotalLaborCost(labors);

        BigDecimal vatAmountMaterials = calculateVat(totalMaterialCost, vatRate);
        BigDecimal vatAmountLabor = calculateVat(totalLaborCost, vatRate);
        BigDecimal totalBeforeMargin = totalMaterialCost.add(totalLaborCost);
        BigDecimal profitMarginAmount = calculateProfit(totalBeforeMargin, project.getProjectMargin());
        BigDecimal finalTotalCost = totalBeforeMargin.add(vatAmountMaterials).add(vatAmountLabor).add(profitMarginAmount);

        // Display results
        System.out.println("--- Résultat du Calcul ---");
        System.out.println("Nom du projet : " + project.getProjectName());
        System.out.println("Client : " + project.getClient().getName());
        System.out.println("Adresse du chantier : " + project.getClient().getAddress());
        System.out.println("Surface : " + project.getSurface() + " m²");
        System.out.println("--- Détail des Coûts ---");

        // Updated method calls with vatRate
        displayMaterials(materials, totalMaterialCost, vatAmountMaterials, vatRate);
        displayLabors(labors, totalLaborCost, vatAmountLabor, vatRate);

        // Final cost summary
        System.out.printf("\n3. Coût total avant marge : %.2f €%n", totalBeforeMargin);
        System.out.printf("4. Marge bénéficiaire (%.0f%%) : %.2f €%n", project.getProjectMargin(), profitMarginAmount);
        System.out.printf("**Coût total final du projet : %.2f €**%n", finalTotalCost);
    }


    private void displayMaterials(List<Material> materials, BigDecimal totalMaterialCost, BigDecimal vatAmount, BigDecimal vatRate) {
        System.out.println("1. Matériaux :");
        for (Material material : materials) {
            BigDecimal materialTotalCost = material.getUnitCost().multiply(material.getQuantity()).add(material.getTransportCost());
            System.out.printf("- %s : %.2f € (quantité : %.2f, coût unitaire : %.2f €/m², qualité : %.2f, transport : %.2f €)%n",
                    material.getName(), materialTotalCost, material.getQuantity(), material.getUnitCost(), material.getQualityCoefficient(), material.getTransportCost());
        }
        System.out.printf("**Coût total des matériaux avant TVA : %.2f €**%n", totalMaterialCost);
        System.out.printf("**Coût total des matériaux avec TVA (%.0f%%) : %.2f €**%n", vatRate, totalMaterialCost.add(vatAmount));
    }

    private void displayLabors(List<Labor> labors, BigDecimal totalLaborCost, BigDecimal vatAmount, BigDecimal vatRate) {
        System.out.println("\n2. Main-d'œuvre :");
        for (Labor labor : labors) {
            BigDecimal laborTotalCost = labor.getHourlyRate().multiply(labor.getHoursWorked());
            System.out.printf("- %s : %.2f € (taux horaire : %.2f €/h, heures travaillées : %.2f h, productivité : %.2f)%n",
                    labor.getName(), laborTotalCost, labor.getHourlyRate(), labor.getHoursWorked(), labor.getProductivityFactor());
        }
        System.out.printf("**Coût total de la main-d'œuvre avant TVA : %.2f €**%n", totalLaborCost);
        System.out.printf("**Coût total de la main-d'œuvre avec TVA (%.0f%%) : %.2f €**%n", vatRate, totalLaborCost.add(vatAmount));
    }


    public void updateProjectStatus(Project project, ProjectStatus newStatus) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        if (newStatus == null) {
            throw new IllegalArgumentException("New status cannot be null");
        }

        // Update the project's status
        project.setProjectStatus(newStatus);

        // Save the updated project in the repository
        projectRepository.updateProject(project);
    }


    public void updateProject(Project updatedProject) {
        if (updatedProject == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        // Update the project in the repository
        projectRepository.updateProject(updatedProject);
    }

}
