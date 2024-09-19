package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Client;
import com.BatiCuisine.repository.interfaces.ClientRepository;
import com.BatiCuisine.config.DataBaseConnection;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ClientRepositoryImpl implements ClientRepository {
    private final Map<UUID, Client> clientCache = new HashMap<>(); // In-memory cache
    private static final String UUID_TYPE = "uuid";

    @Override
    public void addClient(Client client) {
        String query = "INSERT INTO clients (id, name, address, phone, isProfessional) VALUES (uuid_generate_v4(), ?, ?, ?, ?)";  // Include ID generation
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getAddress());
            statement.setString(3, client.getPhone());
            statement.setBoolean(4, client.isProfessional());
            statement.executeUpdate();

            // Update cache after adding
            loadClientToCache(client.getId());
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
            statement.setObject(1, id, Types.OTHER);  // Use setObject() for UUID
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Client client = new Client(
                        (UUID) resultSet.getObject("id"),  // Retrieve UUID from the database
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("isProfessional")
                );
                clientCache.put(id, client);  // Cache the client
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

    private void loadClientToCache(UUID id) {
        String query = "SELECT * FROM clients WHERE id = ?";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, Types.OTHER);  // Use setObject() for UUID
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
