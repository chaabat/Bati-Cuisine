package com.BatiCuisine.service;

import com.BatiCuisine.model.Client;
import com.BatiCuisine.repository.interfaces.ClientRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void addClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        clientRepository.addClient(client);
    }

    public List<Client> getClientsByName(String name) {
        return clientRepository.getClientsByName(name);
    }


}
