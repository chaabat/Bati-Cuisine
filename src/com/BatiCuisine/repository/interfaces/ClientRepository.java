package com.BatiCuisine.repository.interfaces;

import com.BatiCuisine.model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    void addClient(Client client);

    List<Client> getAllClients();

    List<Client> getClientsByName(String name);



}
