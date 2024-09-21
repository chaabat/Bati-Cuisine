package com.BatiCuisine;

import com.BatiCuisine.controller.ConsoleUI;
import com.BatiCuisine.service.*;
import com.BatiCuisine.repository.implementation.ClientRepositoryImpl;
import com.BatiCuisine.repository.implementation.LaborRepositoryImpl;
import com.BatiCuisine.repository.implementation.MaterialRepositoryImpl;
import com.BatiCuisine.repository.implementation.ProjectRepositoryImpl;
import com.BatiCuisine.repository.implementation.QuoteRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        // Initialize repositories
        ProjectRepositoryImpl projectRepository = new ProjectRepositoryImpl();
        MaterialRepositoryImpl materialRepository = new MaterialRepositoryImpl();
        LaborRepositoryImpl laborRepository = new LaborRepositoryImpl();
        ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
        QuoteRepositoryImpl quoteRepository = new QuoteRepositoryImpl();

        // Initialize services with repositories
        MaterialService materialService = new MaterialService(materialRepository);
        LaborService laborService = new LaborService(laborRepository);
        ProjectService projectService = new ProjectService(projectRepository, materialService, laborService);
        ClientService clientService = new ClientService(clientRepository);
        QuoteService quoteService = new QuoteService(quoteRepository,projectRepository);

        // Initialize and show UI
        ConsoleUI consoleUI = new ConsoleUI(projectService, materialService, laborService, clientService);
        consoleUI.showMainMenu();
    }
}
