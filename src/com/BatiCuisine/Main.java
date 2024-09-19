package com.BatiCuisine;

import com.BatiCuisine.controller.ConsoleUI;
import com.BatiCuisine.service.ClientService;
import com.BatiCuisine.service.LaborService;
import com.BatiCuisine.service.MaterialService;
import com.BatiCuisine.service.ProjectService;
import com.BatiCuisine.repository.implementation.ClientRepositoryImpl;
import com.BatiCuisine.repository.implementation.LaborRepositoryImpl;
import com.BatiCuisine.repository.implementation.MaterialRepositoryImpl;
import com.BatiCuisine.repository.implementation.ProjectRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        // Initialize repositories
        ProjectRepositoryImpl projectRepository = new ProjectRepositoryImpl();
        MaterialRepositoryImpl materialRepository = new MaterialRepositoryImpl();
        LaborRepositoryImpl laborRepository = new LaborRepositoryImpl();
        ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();

        // Initialize services with repositories
        MaterialService materialService = new MaterialService(materialRepository);
        LaborService laborService = new LaborService(laborRepository);
        ProjectService projectService = new ProjectService(projectRepository, materialService, laborService);
        ClientService clientService = new ClientService(clientRepository);

        // Initialize and show UI
        ConsoleUI consoleUI = new ConsoleUI(projectService, materialService, laborService, clientService);
        consoleUI.showMainMenu();
    }
}
