package com.BatiCuisine.service;

import com.BatiCuisine.model.Client;
import com.BatiCuisine.repository.interfaces.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientService {
    private final ClientRepository clientRepository;  // Inject the repository

    // Constructor to inject ClientRepository dependency
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // Add a new client (delegates to the repository)
    public void addClient(Client client) {
        clientRepository.addClient(client);
    }

    // Get client by UUID (returns Optional to handle the case where client might not exist)
    public Optional<Client> getClientById(UUID id) {
        return clientRepository.getClientById(id);
    }

    // Get all clients (returns a List of clients)
    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }


}
