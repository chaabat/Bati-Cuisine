package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Material;
import com.BatiCuisine.repository.interfaces.MaterialRepository;
import com.BatiCuisine.config.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MaterialRepositoryImpl implements MaterialRepository {

    @Override
    public void addMaterial(Material material) {
        String query = "INSERT INTO materials (componentId, qualityCoefficient, transportCost) VALUES (uuid_generate_v4(), ?, ?)";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBigDecimal(1, material.getQualityCoefficient());  // Use BigDecimal for precision
            statement.setBigDecimal(2, material.getTransportCost());       // Use BigDecimal for precision

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Material> getMaterialById(UUID id) {
        String query = "SELECT * FROM materials WHERE componentId = ?";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, java.sql.Types.OTHER);  // Set UUID as parameter

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Material material = new Material(
                        (UUID) resultSet.getObject("componentId"),  // UUID
                        resultSet.getString("name"),  // Assuming 'name' is part of Component
                        resultSet.getBigDecimal("unitCost"),  // Assuming 'unitCost' is part of Component
                        resultSet.getBigDecimal("quantity"),  // Assuming 'quantity' is part of Component
                        resultSet.getBigDecimal("taxRate"),  // Assuming 'taxRate' is part of Component
                        (UUID) resultSet.getObject("projectId"),  // UUID
                        resultSet.getBigDecimal("qualityCoefficient"),  // BigDecimal
                        resultSet.getBigDecimal("transportCost")  // BigDecimal
                );
                return Optional.of(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    @Override
    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        String query = "SELECT * FROM materials";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // Create a Material object using the constructor that matches the parameters
                Material material = new Material(
                        (UUID) resultSet.getObject("componentId"),  // UUID
                        resultSet.getString("name"),  // Assuming 'name' is part of Component
                        resultSet.getBigDecimal("unitCost"),  // Assuming 'unitCost' is part of Component
                        resultSet.getBigDecimal("quantity"),  // Assuming 'quantity' is part of Component
                        resultSet.getBigDecimal("taxRate"),  // Assuming 'taxRate' is part of Component
                        (UUID) resultSet.getObject("projectId"),  // UUID
                        resultSet.getBigDecimal("qualityCoefficient"),  // BigDecimal
                        resultSet.getBigDecimal("transportCost")  // BigDecimal
                );
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }





}
