package com.BatiCuisine.controller;

import com.BatiCuisine.model.*;
import com.BatiCuisine.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ConsoleUI {

    private final ProjectService projectService;
    private final MaterialService materialService;
    private final LaborService laborService;
    private final ClientService clientService;
    private final QuoteService quoteService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(ProjectService projectService, MaterialService materialService, LaborService laborService, ClientService clientService, QuoteService quoteService) {
        this.projectService = projectService;
        this.materialService = materialService;
        this.laborService = laborService;
        this.clientService = clientService;
        this.quoteService = quoteService;

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

        // Remove profit margin input during project creation
        // Profit margin will be null initially and set later during the cost calculation
        BigDecimal projectMargin = null;

        System.out.print("Entrez le coût total du projet : ");
        BigDecimal totalCost = readPositiveBigDecimal("Coût total invalide. Veuillez entrer une valeur positive : ");

        // Create the new project with null profit margin
        Project newProject = new Project(
                projectName,
                projectMargin,  // Set profit margin to null initially
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
            return;
        }

        // Display the table header (without Email)
        System.out.printf("%-5s %-20s %-30s %-20s%n", "N°", "Nom du Client", "Adresse", "Nom du Projet");
        System.out.println("------------------------------------------------------------------------------------");

        // Display project information in table format
        int index = 1;
        for (Project project : projects) {
            Client client = project.getClient();
            System.out.printf("%-5d %-20s %-30s %-20s%n",
                    index++,
                    client.getName(),
                    client.getAddress(),
                    project.getProjectName());
        }

        // Loop to view project details
        boolean viewAnotherProject = false;
        do {
            // Ask the user to choose a project
            System.out.print("\nEntrez le numéro du projet pour afficher les détails (ou 0 pour quitter) : ");
            int selectedProjectIndex = readPositiveInt("Numéro de projet invalide. Veuillez entrer un numéro valide.");

            if (selectedProjectIndex == 0) {
                System.out.println("Retour au menu principal.");
                return;
            }

            if (selectedProjectIndex < 1 || selectedProjectIndex > projects.size()) {
                System.out.println("Numéro de projet invalide. Veuillez réessayer.");
                continue;
            }

            // Display the selected project's details
            Project selectedProject = projects.get(selectedProjectIndex - 1);
            displayProjectDetails(selectedProject);

            // Ask if the user wants to view another project
            System.out.print("\nSouhaitez-vous afficher les détails d'un autre projet ? (y/n) : ");
            String viewAnother = scanner.nextLine();
            viewAnotherProject = viewAnother.equalsIgnoreCase("y");

        } while (viewAnotherProject);
    }


    // Helper method to display detailed project information
    private void displayProjectDetails(Project project) {
        // Display general project details
        System.out.println("\n--- Détails du Projet ---");
        System.out.println("Nom du Client : " + project.getClient().getName());
        System.out.println("Adresse du Client : " + project.getClient().getAddress());
        System.out.println("Nom du Projet : " + project.getProjectName());
        System.out.println("Surface du Projet : " + project.getSurface());
        System.out.println("Coût total estimé : " + project.getTotalCost());
        System.out.println("Statut du Projet : " + project.getStatus());

        // Fetch and display materials
        List<Material> materials = materialService.findByProjectId(project.getId());
        System.out.println("\n--- Matériaux ---");
        if (materials.isEmpty()) {
            System.out.println("Aucun matériel trouvé.");
        } else {
            for (Material material : materials) {
                System.out.printf("Nom : %s, Quantité : %.2f, Coût par unité : %.2f, Coût total : %.2f%n",
                        material.getName(),
                        material.getQuantity(),
                        material.getUnitCost(),
                        material.getQuantity().multiply(material.getUnitCost()));
            }
        }

        // Fetch and display labor
        List<Labor> labors = laborService.findByProjectId(project.getId());
        System.out.println("\n--- Main d'œuvre ---");
        if (labors.isEmpty()) {
            System.out.println("Aucune main d'œuvre trouvée.");
        } else {
            for (Labor labor : labors) {
                System.out.printf("Nom : %s, Heures travaillées : %.2f, Taux horaire : %.2f, Coût total : %.2f%n",
                        labor.getName(),
                        labor.getHoursWorked(),
                        labor.getHourlyRate(),
                        labor.getHoursWorked().multiply(labor.getHourlyRate()));
            }
        }
    }



    private void calculateProjectCost() {
        System.out.print("Entrez l'identifiant du projet : ");
        String projectId = scanner.nextLine();

        Optional<Project> projectOpt = projectService.getProjectById(UUID.fromString(projectId));
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();

            // Ask about applying VAT
            System.out.print("Souhaitez-vous appliquer une TVA au projet ? (y/n) : ");
            String applyVatInput = scanner.nextLine();
            BigDecimal vatRate = BigDecimal.ZERO;

            if (applyVatInput.equalsIgnoreCase("y")) {
                System.out.print("Entrez le pourcentage de TVA (%) : ");
                vatRate = readPositiveBigDecimal("Pourcentage de TVA invalide. Veuillez entrer une valeur positive : ");
            }

            // Ask about applying profit margin
            System.out.print("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) : ");
            String applyMarginInput = scanner.nextLine();
            BigDecimal profitMargin = BigDecimal.ZERO;

            if (applyMarginInput.equalsIgnoreCase("y")) {
                System.out.print("Entrez le pourcentage de marge bénéficiaire (%) : ");
                profitMargin = readPositiveBigDecimal("Pourcentage de marge bénéficiaire invalide. Veuillez entrer une valeur positive : ");
            }

            // Calculate project cost
            System.out.println("Calcul du coût en cours...");
            Optional<Project> updatedProjectOpt = projectService.calculateProjectCost(project, vatRate, profitMargin);

            if (updatedProjectOpt.isPresent()) {
                Project updatedProject = updatedProjectOpt.get();
                projectService.displayProjectCostDetails(updatedProject, vatRate);

                // Save the project with updated profit margin to the database
                projectService.updateProject(updatedProject);

                // Handle saving the quote with the calculated project cost
                handleQuoteSaving(updatedProject);
            } else {
                System.out.println("Erreur dans le calcul du coût du projet.");
            }
        } else {
            System.out.println("Projet non trouvé.");
        }
    }



    private void handleQuoteSaving(Project project) {
        System.out.println("--- Enregistrement du Devis ---");

        // Formatting the date to 'dd/MM/yyyy' format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate issueDate = null;
        LocalDate validityDate = null;

        // Get and parse the issue date
        while (issueDate == null) {
            System.out.print("Entrez la date d'émission du devis (format : jj/mm/aaaa) : ");
            String issueDateString = scanner.nextLine();
            try {
                issueDate = LocalDate.parse(issueDateString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Date invalide. Veuillez entrer la date dans le format jj/mm/aaaa.");
            }
        }

        // Get and parse the validity date
        while (validityDate == null) {
            System.out.print("Entrez la date de validité du devis (format : jj/mm/aaaa) : ");
            String validityDateString = scanner.nextLine();
            try {
                validityDate = LocalDate.parse(validityDateString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Date invalide. Veuillez entrer la date dans le format jj/mm/aaaa.");
            }
        }

        // Use the total project cost already calculated
        BigDecimal estimatedAmount = project.getTotalCost();

        // Create the quote object
        Quote quote = new Quote(
                estimatedAmount,    // The total cost from the project
                issueDate,          // LocalDate for the issue date
                validityDate,       // LocalDate for the validity date
                false,              // Initially, the quote is not accepted
                project             // Pass the entire Project object
        );

        // Save the quote in the database
        quoteService.addQuote(quote);
        System.out.println("Devis enregistré avec succès !");

        // Ask if the client accepts the quote
        System.out.print("Acceptez-vous le devis ? (oui/non) : ");
        String acceptanceInput = scanner.nextLine();

        // Update the project status based on acceptance
        boolean isAccepted = acceptanceInput.equalsIgnoreCase("oui");
        project.setProjectStatus(isAccepted ? ProjectStatus.COMPLETED : ProjectStatus.CANCELLED);
        projectService.updateProjectStatus(project, project.getProjectStatus());

        System.out.println("Le projet est maintenant marqué comme " + (isAccepted ? "terminé !" : "annulé !"));
    }


    // Helper method to safely read positive integers
    private int readPositiveInt(String errorMessage) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= 0) {
                    return input;
                } else {
                    System.out.println(errorMessage);
                }
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
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