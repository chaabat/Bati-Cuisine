package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Client;
import com.BatiCuisine.repository.interfaces.ClientRepository;
import com.BatiCuisine.config.DataBaseConnection;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ClientRepositoryImpl implements ClientRepository {
    private final Map<UUID, Client> clientCache = new HashMap<>();

    @Override
    public void addClient(Client client) {
        String query = "INSERT INTO clients (name, address, phone, isProfessional) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getAddress());
            statement.setString(3, client.getPhone());
            statement.setBoolean(4, client.isProfessional());


            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UUID generatedId = (UUID) resultSet.getObject("id");
                client.setId(generatedId);
                clientCache.put(generatedId, client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> getAllClients() {
        return new ArrayList<>(clientCache.values());
    }

    @Override
    public List<Client> getClientsByName(String name) {
        List<Client> clients = new ArrayList<>();

        // Check cache first
        clients.addAll(clientCache.values().stream()
                .filter(client -> client.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList()));


        String query = "SELECT * FROM clients WHERE LOWER(name) = LOWER(?)";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Client client = new Client(
                        (UUID) resultSet.getObject("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("isProfessional")
                );
                clientCache.put(client.getId(), client);
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }



}
