package com.BatiCuisine.repository.interfaces;

import com.BatiCuisine.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    void addClient(Client client);
    Optional<Client> getClientById(UUID id);
    List<Client> getAllClients();
    Optional<Client> getClientByName(String name);  // Ensure this method is implemented
}
