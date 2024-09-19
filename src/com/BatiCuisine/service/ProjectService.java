package com.BatiCuisine.service;

import com.BatiCuisine.model.Project;
import com.BatiCuisine.model.Material;
import com.BatiCuisine.model.Labor;
import com.BatiCuisine.repository.interfaces.ProjectRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MaterialService materialService;  // Ensure this is initialized
    private final LaborService laborService;        // Ensure this is initialized

    /**
     * Constructor to inject ProjectRepository, MaterialService, and LaborService dependencies.
     *
     * @param projectRepository The repository to manage projects.
     * @param materialService The service to manage materials.
     * @param laborService The service to manage labor.
     */
    public ProjectService(ProjectRepository projectRepository, MaterialService materialService, LaborService laborService) {
        this.projectRepository = projectRepository;
        this.materialService = materialService;
        this.laborService = laborService;
    }

    /**
     * Adds a new project by delegating to the repository.
     *
     * @param project The project to add.
     */
    public void addProject(Project project) {
        projectRepository.addProject(project);
    }

    /**
     * Retrieves a project by its UUID.
     *
     * @param id The UUID of the project.
     * @return An Optional containing the project if found, or empty if not.
     */
    public Optional<Project> getProjectById(UUID id) {
        return projectRepository.getProjectById(id);
    }

    /**
     * Retrieves all projects.
     *
     * @return A list of all projects.
     */
    public List<Project> listAllProjects() {
        return projectRepository.getAllProjects();
    }

    /**
     * Calculates the total cost of a project, including materials, labor, VAT, and profit margin.
     *
     * @param project The project for which to calculate the cost.
     * @param vatRate The VAT rate to apply.
     * @param profitMargin The profit margin to apply.
     * @return The final total cost of the project.
     */
    public BigDecimal calculateTotalProjectCost(Project project, BigDecimal vatRate, BigDecimal profitMargin) {
        // Ensure project has materials and labor
        if (project.getMaterials() == null) {
            project.setMaterials(new ArrayList<>());
        }
        if (project.getLabors() == null) {
            project.setLabors(new ArrayList<>());
        }

        BigDecimal totalMaterialCost = materialService.calculateTotalMaterialCost(project.getMaterials());
        BigDecimal totalLaborCost = laborService.calculateTotalLaborCost(project.getLabors());

        // Cost calculation
        BigDecimal totalCostBeforeVat = totalMaterialCost.add(totalLaborCost);
        BigDecimal vatAmount = vatRate.multiply(totalCostBeforeVat).divide(BigDecimal.valueOf(100));
        BigDecimal profitAmount = profitMargin.multiply(totalCostBeforeVat).divide(BigDecimal.valueOf(100));
        BigDecimal totalCostWithVat = totalCostBeforeVat.add(vatAmount);
        BigDecimal totalCostFinal = totalCostWithVat.add(profitAmount);

        return totalCostFinal;
    }

    /**
     * Displays the project cost details.
     *
     * @param project The project whose cost details are to be displayed.
     * @param totalCost The total cost of the project.
     */
    public void displayProjectCostDetails(Project project, BigDecimal totalCost) {
        System.out.println("--- Résultat du Calcul ---");
        System.out.println("Nom du projet : " + project.getProjectName());
        System.out.println("Client : " + project.getClient().getName());
        System.out.println("Adresse du chantier : " + project.getClient().getAddress());
        System.out.println("Surface : " + project.getSurface() + " m²");

        System.out.println("--- Détail des Coûts ---");
        System.out.println("Coût total final du projet : " + totalCost + " €");
    }


}
