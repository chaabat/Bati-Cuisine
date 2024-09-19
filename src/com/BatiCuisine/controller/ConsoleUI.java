package com.BatiCuisine.controller;

import com.BatiCuisine.model.*;
import com.BatiCuisine.service.ClientService;
import com.BatiCuisine.service.LaborService;
import com.BatiCuisine.service.MaterialService;
import com.BatiCuisine.service.ProjectService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleUI {

    private final ProjectService projectService;
    private final MaterialService materialService;
    private final LaborService laborService;
    private final ClientService clientService; // Add this
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(ProjectService projectService, MaterialService materialService, LaborService laborService, ClientService clientService) {
        this.projectService = projectService;
        this.materialService = materialService;
        this.laborService = laborService;
        this.clientService = clientService; // Initialize this
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

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

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
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private void createNewProject() {
        System.out.println("--- Recherche de client ---");
        System.out.println("Souhaitez-vous chercher un client existant ou en ajouter un nouveau ?");
        System.out.println("1. Chercher un client existant");
        System.out.println("2. Ajouter un nouveau client");
        System.out.print("Choisissez une option : ");
        int clientChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Client client;
        if (clientChoice == 1) {
            client = searchExistingClient();
        } else if (clientChoice == 2) {
            client = createNewClient();
        } else {
            System.out.println("Option invalide.");
            return;
        }

        if (client == null) {
            System.out.println("Client non sélectionné.");
            return;
        }

        System.out.println("--- Création d'un Nouveau Projet ---");
        System.out.print("Entrez le nom du projet : ");
        String projectName = scanner.nextLine();
        System.out.print("Entrez la marge bénéficiaire du projet (en %) : ");
        BigDecimal projectMargin = scanner.nextBigDecimal();
        System.out.print("Entrez le coût total du projet : ");
        BigDecimal totalCost = scanner.nextBigDecimal();
        scanner.nextLine();  // Consume newline

        // Assuming ProjectStatus is an enum with a value of "IN_PROGRESS"
        ProjectStatus status = ProjectStatus.IN_PROGRESS;

        Project newProject = new Project(projectName, projectMargin, status, totalCost, client);

        // Handle materials and labor
        System.out.println("--- Ajout des matériaux ---");
        addMaterials(newProject);

        System.out.println("--- Ajout de la main-d'œuvre ---");
        addLabor(newProject);

        // Add the project to the service
        projectService.addProject(newProject);
        System.out.println("Projet créé avec succès !");
    }

    private Client createNewClient() {
        return null; // This method should have logic to create and return a new client
    }

    private Client searchExistingClient() {
        System.out.print("Entrez le nom du client : ");
        String clientName = scanner.nextLine();

        // Fetch client using the service instance
        Optional<Client> client = clientService.getClientByName(clientName);

        if (client.isPresent()) {
            Client foundClient = client.get();
            System.out.println("Client trouvé !");
            System.out.println("Nom : " + foundClient.getName());
            System.out.println("Adresse : " + foundClient.getAddress());
            System.out.println("Numéro de téléphone : " + foundClient.getPhone());
            System.out.print("Souhaitez-vous continuer avec ce client ? (y/n) : ");

            if (scanner.nextLine().equalsIgnoreCase("y")) {
                return foundClient;
            } else {
                System.out.println("Client non sélectionné. Retour au menu.");
            }
        } else {
            System.out.println("Client non trouvé.");
            // Optional: Ask if user wants to add a new client
            System.out.print("Souhaitez-vous ajouter un nouveau client ? (y/n) : ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                return addNewClient(); // Updated to call the method to add a new client
            }
        }
        return null;
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

        Client newClient = new Client(clientName, clientAddress, clientPhone, isProfessional);
        clientService.addClient(newClient); // Use the instance method
        System.out.println("Nouveau client ajouté avec succès !");
        return newClient;
    }

    private void addMaterials(Project project) {
        boolean addMoreMaterials = true;
        while (addMoreMaterials) {
            System.out.print("Entrez le nom du matériau : ");
            String materialName = scanner.nextLine();
            System.out.print("Entrez la quantité de ce matériau : ");
            BigDecimal quantity = scanner.nextBigDecimal();
            System.out.print("Entrez le coût unitaire de ce matériau (€/unité) : ");
            BigDecimal unitCost = scanner.nextBigDecimal();
            System.out.print("Entrez le coût de transport de ce matériau (€) : ");
            BigDecimal transportCost = scanner.nextBigDecimal();
            System.out.print("Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : ");
            BigDecimal qualityCoefficient = scanner.nextBigDecimal();
            scanner.nextLine();  // Consume newline

            // Assuming taxRate should be zero for this use case
            BigDecimal taxRate = BigDecimal.ZERO;
            Material material = new Material(materialName, unitCost, quantity, taxRate, project.getId(), qualityCoefficient, transportCost);
            materialService.addMaterial(material);
            project.addMaterial(material);
            System.out.println("Matériau ajouté avec succès !");

            System.out.print("Voulez-vous ajouter un autre matériau ? (y/n) : ");
            addMoreMaterials = scanner.nextLine().equalsIgnoreCase("y");
        }
    }

    private void addLabor(Project project) {
        boolean addMoreLabor = true;
        while (addMoreLabor) {
            System.out.print("Entrez le taux horaire de cette main-d'œuvre (€/h) : ");
            BigDecimal hourlyRate = scanner.nextBigDecimal();
            System.out.print("Entrez le nombre d'heures travaillées : ");
            BigDecimal hoursWorked = scanner.nextBigDecimal();
            System.out.print("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");
            BigDecimal productivityFactor = scanner.nextBigDecimal();
            scanner.nextLine();  // Consume newline

            Labor labor = new Labor(hourlyRate, hoursWorked, productivityFactor);
            laborService.addLabor(labor);
            project.addLabor(labor);
            System.out.println("Main-d'œuvre ajoutée avec succès !");

            System.out.print("Voulez-vous ajouter un autre type de main-d'œuvre ? (y/n) : ");
            addMoreLabor = scanner.nextLine().equalsIgnoreCase("y");
        }
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
            BigDecimal vatRate = scanner.nextBigDecimal();
            System.out.print("Entrez la marge bénéficiaire en pourcentage : ");
            BigDecimal profitMargin = scanner.nextBigDecimal();
            scanner.nextLine();  // Consume newline

            BigDecimal totalCost = projectService.calculateTotalProjectCost(project, vatRate, profitMargin);
            projectService.displayProjectCostDetails(project, totalCost);
        } else {
            System.out.println("Projet non trouvé.");
        }
    }

}
