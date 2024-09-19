package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Client;
import com.BatiCuisine.repository.interfaces.ClientRepository;
import com.BatiCuisine.config.DataBaseConnection;

import java.sql.*;
import java.util.*;

public class ClientRepositoryImpl implements ClientRepository {
    private final Map<UUID, Client> clientCache = new HashMap<>(); // In-memory cache

    @Override
    public void addClient(Client client) {
        String query = "INSERT INTO clients (name, address, phone, isProfessional) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getAddress());
            statement.setString(3, client.getPhone());
            statement.setBoolean(4, client.isProfessional());

            // Execute the insert and retrieve the generated client ID
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UUID generatedId = (UUID) resultSet.getObject("id");
                client.setId(generatedId);  // Set the ID back to the client object
                clientCache.put(generatedId, client); // Cache the client
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Optional<Client> getClientById(UUID id) {
        if (clientCache.containsKey(id)) {
            return Optional.of(clientCache.get(id));
        }

        String query = "SELECT * FROM clients WHERE id = ?";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, Types.OTHER);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Client client = new Client(
                        (UUID) resultSet.getObject("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("isProfessional")
                );
                clientCache.put(id, client);
                return Optional.of(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Client> getAllClients() {
        return new ArrayList<>(clientCache.values());
    }

    @Override
    public Optional<Client> getClientByName(String name) {
        return clientCache.values().stream()
                .filter(client -> client.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
