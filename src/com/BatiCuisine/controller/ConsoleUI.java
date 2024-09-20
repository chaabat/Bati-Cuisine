package com.BatiCuisine.controller;

import com.BatiCuisine.model.*;
import com.BatiCuisine.service.ClientService;
import com.BatiCuisine.service.LaborService;
import com.BatiCuisine.service.MaterialService;
import com.BatiCuisine.service.ProjectService;

import java.math.BigDecimal;
import java.util.*;

public class ConsoleUI {

    private final ProjectService projectService;
    private final MaterialService materialService;
    private final LaborService laborService;
    private final ClientService clientService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(ProjectService projectService, MaterialService materialService, LaborService laborService, ClientService clientService) {
        this.projectService = projectService;
        this.materialService = materialService;
        this.laborService = laborService;
        this.clientService = clientService;
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("=== Bienvenue dans l'application de gestion des projets de rénovation de cuisines ===");
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Créer un nouveau projet");
            System.out.println("2. Afficher les projets existants");
            System.out.println("3. Calculer le coût d'un projet");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");

            int choice = readIntInRange(1, 4, "Option invalide. Veuillez réessayer.");

            switch (choice) {
                case 1:
                    createNewProject();
                    break;
                case 2:
                    viewExistingProjects();
                    break;
                case 3:
                    calculateProjectCost();
                    break;
                case 4:
                    System.out.println("Merci d'avoir utilisé l'application!");
                    return;  // Exit the loop
            }
        }
    }

    private void createNewProject() {
        System.out.println("--- Création d'un Nouveau Projet ---");

        // Get the client
        Client client = null;
        System.out.println("--- Recherche de client ---");
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.print("Choisissez une option : ");

        int clientChoice = readIntInRange(1, 2, "Option invalide. Veuillez réessayer.");
        if (clientChoice == 1) {
            System.out.print("Entrez le nom du client : ");
            String clientName = scanner.nextLine();

            List<Client> clients = clientService.getClientsByName(clientName);
            if (!clients.isEmpty()) {
                if (clients.size() == 1) {
                    client = clients.get(0);
                    System.out.println("Client trouvé !");
                    System.out.println("Nom : " + client.getName());
                    System.out.println("Adresse : " + client.getAddress());
                    System.out.println("Numéro de téléphone : " + client.getPhone());
                    System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");
                    if (!scanner.nextLine().equalsIgnoreCase("y")) {
                        System.out.println("Aucun client sélectionné. Annulation de la création du projet.");
                        return;
                    }
                } else {
                    System.out.println("Plusieurs clients trouvés :");
                    for (int i = 0; i < clients.size(); i++) {
                        Client foundClient = clients.get(i);
                        System.out.println((i + 1) + ". " + foundClient.getName());
                        System.out.println("   Adresse : " + foundClient.getAddress());
                        System.out.println("   Numéro de téléphone : " + foundClient.getPhone());
                    }
                    System.out.print("Veuillez sélectionner un client en entrant le numéro : ");
                    int selectedIndex = readIntInRange(1, clients.size(), "Choix invalide. Veuillez réessayer.") - 1;
                    client = clients.get(selectedIndex);
                }
            } else {
                System.out.println("Client non trouvé.");
                return;
            }
        } else {
            client = addNewClient();
        }

        // Get project type
        System.out.println("Choisissez le type de projet :");
        System.out.println("1. Rénovation");
        System.out.println("2. Construction");
        System.out.print("Choisissez une option : ");
        int projectTypeChoice = readIntInRange(1, 2, "Option invalide. Veuillez réessayer.");
        String projectType = (projectTypeChoice == 1) ? "Rénovation" : "Construction";

        // Get project details
        System.out.print("Entrez le nom du projet : ");
        String projectName = scanner.nextLine();
        System.out.print("Entrez la surface du projet (en m²) : ");
        BigDecimal surface = readPositiveBigDecimal("Surface invalide. Veuillez entrer une valeur positive : ");
        System.out.print("Entrez la marge bénéficiaire du projet (en %) : ");
        BigDecimal projectMargin = readPositiveBigDecimal("Marge bénéficiaire invalide. Veuillez entrer une valeur positive : ");
        System.out.print("Entrez le coût total du projet : ");
        BigDecimal totalCost = readPositiveBigDecimal("Coût total invalide. Veuillez entrer une valeur positive : ");

        // Create the new project
        Project newProject = new Project(
                projectName,
                projectMargin,
                ProjectStatus.IN_PROGRESS,
                totalCost,
                client,
                surface,
                projectType
        );

        // Add the project to the repository
        projectService.addProject(newProject);
        System.out.println("Projet créé avec succès !");

        // Add materials and labor
        System.out.println("--- Ajout des matériaux ---");
        addMaterials(newProject);
        System.out.println("--- Ajout de la main-d'œuvre ---");
        addLabor(newProject);
    }


    private void addMaterials(Project project) {
        boolean addMoreMaterials = true;
        while (addMoreMaterials) {
            System.out.print("Entrez le nom du matériau : ");
            String materialName = scanner.nextLine();
            System.out.print("Entrez la quantité de ce matériau : ");
            BigDecimal quantity = readPositiveBigDecimal("Quantité invalide. Veuillez entrer une valeur positive : ");
            System.out.print("Entrez le coût unitaire de ce matériau (€/unité) : ");
            BigDecimal unitCost = readPositiveBigDecimal("Coût unitaire invalide. Veuillez entrer une valeur positive : ");
            System.out.print("Entrez le coût de transport de ce matériau (€) : ");
            BigDecimal transportCost = readPositiveBigDecimal("Coût de transport invalide. Veuillez entrer une valeur positive : ");
            System.out.print("Entrez le coefficient de qualité du matériau : ");
            BigDecimal qualityCoefficient = readPositiveBigDecimal("Coefficient invalide. Veuillez entrer une valeur positive : ");

            Material material = new Material(
                    UUID.randomUUID(), materialName, unitCost, quantity, new BigDecimal("0.20"), // Default taxRate
                    project.getId(), qualityCoefficient, transportCost
            );

            materialService.addMaterial(material);
            System.out.println("Matériau ajouté avec succès !");

            System.out.print("Voulez-vous ajouter un autre matériau ? (oui/non) : ");
            addMoreMaterials = scanner.nextLine().equalsIgnoreCase("oui");
        }
    }

    private void addLabor(Project project) {
        boolean addMoreLabor = true;
        while (addMoreLabor) {
            System.out.print("Entrez le type de main-d'œuvre (e.g., Ouvrier de base) : ");
            String laborType = scanner.nextLine();
            System.out.print("Entrez le taux horaire de cette main-d'œuvre (€/h) : ");
            BigDecimal hourlyRate = readPositiveBigDecimal("Taux horaire invalide. Veuillez entrer une valeur positive : ");
            System.out.print("Entrez le nombre d'heures travaillées : ");
            BigDecimal hoursWorked = readPositiveBigDecimal("Nombre d'heures invalide. Veuillez entrer une valeur positive : ");
            System.out.print("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");
            BigDecimal productivityFactor = readPositiveBigDecimal("Facteur de productivité invalide. Veuillez entrer une valeur positive : ");

            Labor labor = new Labor(
                    UUID.randomUUID(), laborType, BigDecimal.ZERO, BigDecimal.ZERO, // Placeholder values
                    new BigDecimal("0.20"), // Default taxRate
                    project.getId(), hourlyRate, hoursWorked, productivityFactor
            );

            laborService.addLabor(labor);
            System.out.println("Main-d'œuvre ajoutée avec succès !");

            System.out.print("Voulez-vous ajouter un autre type de main-d'œuvre ? (oui/non) : ");
            addMoreLabor = scanner.nextLine().equalsIgnoreCase("oui");
        }
    }

    private Client addNewClient() {
        System.out.print("Entrez le nom du nouveau client : ");
        String clientName = scanner.nextLine();
        System.out.print("Entrez l'adresse du client : ");
        String clientAddress = scanner.nextLine();
        System.out.print("Entrez le numéro de téléphone du client : ");
        String clientPhone = scanner.nextLine();
        System.out.print("Le client est-il un professionnel ? (true/false) : ");
        boolean isProfessional = scanner.nextBoolean();
        scanner.nextLine();  // Consume newline

        Client newClient = new Client(UUID.randomUUID(), clientName, clientAddress, clientPhone, isProfessional);
        clientService.addClient(newClient);
        System.out.println("Nouveau client ajouté avec succès !");
        return newClient;
    }

    private void viewExistingProjects() {
        List<Project> projects = projectService.listAllProjects();
        if (projects.isEmpty()) {
            System.out.println("Aucun projet trouvé.");
        } else {
            for (Project project : projects) {
                System.out.println(project);
            }
        }
    }

    private void calculateProjectCost() {
        System.out.print("Entrez l'identifiant du projet : ");
        String projectId = scanner.nextLine();

        Optional<Project> projectOpt = projectService.getProjectById(UUID.fromString(projectId));
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            System.out.print("Entrez le taux de TVA en pourcentage : ");
            BigDecimal vatRate = readPositiveBigDecimal("Taux de TVA invalide. Veuillez entrer une valeur positive : ");
            System.out.print("Entrez la marge bénéficiaire en pourcentage : ");
            BigDecimal profitMargin = readPositiveBigDecimal("Marge bénéficiaire invalide. Veuillez entrer une valeur positive : ");

            BigDecimal totalCost = projectService.calculateTotalProjectCost(project, vatRate, profitMargin);
            projectService.displayProjectCostDetails(project, totalCost);
        } else {
            System.out.println("Projet non trouvé.");
        }
    }

    private BigDecimal readPositiveBigDecimal(String errorMessage) {
        BigDecimal value;
        while (true) {
            try {
                value = scanner.nextBigDecimal();
                if (value.compareTo(BigDecimal.ZERO) > 0) {
                    scanner.nextLine();  // Consume the newline
                    return value;
                } else {
                    System.out.print(errorMessage);
                }
            } catch (InputMismatchException e) {
                System.out.print(errorMessage);
                scanner.nextLine();  // Consume the invalid input
            }
        }
    }

    private int readIntInRange(int min, int max, String errorMessage) {
        int choice;
        while (true) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.print(errorMessage);
                }
            } catch (InputMismatchException e) {
                System.out.print(errorMessage);
                scanner.nextLine();  // Consume the invalid input
            }
        }
    }
}
