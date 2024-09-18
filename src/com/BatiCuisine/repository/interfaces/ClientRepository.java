package com.BatiCuisine.repository.interfaces;

import com.BatiCuisine.model.Client;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    void addClient(Client client);
    Optional<Client> getClientById(int id);
    List<Client> getAllClients();
}
