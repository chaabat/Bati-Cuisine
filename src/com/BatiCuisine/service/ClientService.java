package com.BatiCuisine.service;

import com.BatiCuisine.model.Client;
import com.BatiCuisine.repository.interfaces.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientService {
    private final ClientRepository clientRepository;  // Inject the repository

    /**
     * Constructor to inject ClientRepository dependency.
     *
     * @param clientRepository the client repository
     */
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Add a new client to the repository.
     *
     * @param client the client to add
     */
    public void addClient(Client client) {
        // Optionally, add validation or preprocessing here
        clientRepository.addClient(client);
    }

    /**
     * Get a client by UUID.
     *
     * @param id the UUID of the client
     * @return an Optional containing the client if found, otherwise empty
     */
    public Optional<Client> getClientById(UUID id) {
        return clientRepository.getClientById(id);
    }

    /**
     * Get all clients from the repository.
     *
     * @return a list of all clients
     */
    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    /**
     * Get a client by name.
     *
     * @param name the name of the client
     * @return an Optional containing the client if found, otherwise empty
     */
    public Optional<Client> getClientByName(String name) {
        return clientRepository.getClientByName(name);
    }
}
