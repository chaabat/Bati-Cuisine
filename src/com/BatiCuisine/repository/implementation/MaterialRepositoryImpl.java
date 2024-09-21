package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Material;
import com.BatiCuisine.repository.interfaces.MaterialRepository;
import com.BatiCuisine.config.DataBaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MaterialRepositoryImpl implements MaterialRepository {

    @Override
    public void addMaterial(Material material) {
        String insertComponentQuery = "INSERT INTO component (name, unitCost, quantity, componentType, taxRate, projectId) VALUES (?, ?, ?, 'Material', ?, ?) RETURNING id";
        String insertMaterialQuery = "INSERT INTO materials (componentId, qualityCoefficient, transportCost) VALUES (?, ?, ?)";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement insertComponentStmt = connection.prepareStatement(insertComponentQuery);
             PreparedStatement insertMaterialStmt = connection.prepareStatement(insertMaterialQuery)) {

            // Set parameters for the component insertion
            insertComponentStmt.setString(1, material.getName());
            insertComponentStmt.setBigDecimal(2, material.getUnitCost());
            insertComponentStmt.setBigDecimal(3, material.getQuantity());
            insertComponentStmt.setBigDecimal(4, material.getTaxRate());
            insertComponentStmt.setObject(5, material.getProjectId(), java.sql.Types.OTHER);

            // Execute component insertion and get the generated componentId
            ResultSet resultSet = insertComponentStmt.executeQuery();
            if (resultSet.next()) {
                UUID componentId = (UUID) resultSet.getObject("id");

                // Set parameters for the material insertion
                insertMaterialStmt.setObject(1, componentId, java.sql.Types.OTHER);
                insertMaterialStmt.setBigDecimal(2, material.getQualityCoefficient());
                insertMaterialStmt.setBigDecimal(3, material.getTransportCost());

                // Execute material insertion
                insertMaterialStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Material> getMaterialById(UUID id) {
        String query = "SELECT c.id AS componentId, c.name, c.unitCost, c.quantity, c.taxRate, c.projectId, m.qualityCoefficient, m.transportCost " +
                "FROM component c JOIN materials m ON c.id = m.componentId WHERE c.id = ?";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, java.sql.Types.OTHER);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Material material = new Material(
                        (UUID) resultSet.getObject("componentId"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("unitCost"),
                        resultSet.getBigDecimal("quantity"),
                        resultSet.getBigDecimal("taxRate"),
                        (UUID) resultSet.getObject("projectId"),
                        resultSet.getBigDecimal("qualityCoefficient"),
                        resultSet.getBigDecimal("transportCost")
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
        String query = "SELECT c.id AS componentId, c.name, c.unitCost, c.quantity, c.taxRate, c.projectId, m.qualityCoefficient, m.transportCost " +
                "FROM component c JOIN materials m ON c.id = m.componentId";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Material material = new Material(
                        (UUID) resultSet.getObject("componentId"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("unitCost"),
                        resultSet.getBigDecimal("quantity"),
                        resultSet.getBigDecimal("taxRate"),
                        (UUID) resultSet.getObject("projectId"),
                        resultSet.getBigDecimal("qualityCoefficient"),
                        resultSet.getBigDecimal("transportCost")
                );
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    @Override
    public void updateMaterial(Material material) {
        String updateComponentQuery = "UPDATE component SET name = ?, unitCost = ?, quantity = ?, taxRate = ?, projectId = ? WHERE id = ?";
        String updateMaterialQuery = "UPDATE materials SET qualityCoefficient = ?, transportCost = ? WHERE componentId = ?";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement componentStatement = connection.prepareStatement(updateComponentQuery);
             PreparedStatement materialStatement = connection.prepareStatement(updateMaterialQuery)) {

            // Update component table
            componentStatement.setString(1, material.getName());
            componentStatement.setBigDecimal(2, material.getUnitCost());
            componentStatement.setBigDecimal(3, material.getQuantity());
            componentStatement.setBigDecimal(4, material.getTaxRate());
            componentStatement.setObject(5, material.getProjectId(), java.sql.Types.OTHER);
            componentStatement.setObject(6, material.getId(), java.sql.Types.OTHER); // Use the material's component ID
            componentStatement.executeUpdate();

            // Update materials table
            materialStatement.setBigDecimal(1, material.getQualityCoefficient());
            materialStatement.setBigDecimal(2, material.getTransportCost());
            materialStatement.setObject(3, material.getId(), java.sql.Types.OTHER); // Use the material's component ID
            materialStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMaterial(UUID componentId) {
        String deleteMaterialQuery = "DELETE FROM materials WHERE componentId = ?";
        String deleteComponentQuery = "DELETE FROM component WHERE id = ?";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement materialStatement = connection.prepareStatement(deleteMaterialQuery);
             PreparedStatement componentStatement = connection.prepareStatement(deleteComponentQuery)) {

            // Delete from materials table
            materialStatement.setObject(1, componentId, java.sql.Types.OTHER);
            materialStatement.executeUpdate();

            // Delete from component table
            componentStatement.setObject(1, componentId, java.sql.Types.OTHER);
            componentStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Material> findByProjectId(UUID projectId) {
        List<Material> materials = new ArrayList<>();
        String query = "SELECT c.id AS componentId, c.name, c.unitCost, c.quantity, c.taxRate, c.projectId, m.qualityCoefficient, m.transportCost " +
                "FROM component c JOIN materials m ON c.id = m.componentId WHERE c.projectId = ?";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, projectId, java.sql.Types.OTHER); // Set the projectId parameter
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Material material = new Material(
                        (UUID) resultSet.getObject("componentId"), // UUID
                        resultSet.getString("name"),                // String (name from Component)
                        resultSet.getBigDecimal("unitCost"),        // BigDecimal (unitCost from Component)
                        resultSet.getBigDecimal("quantity"),        // BigDecimal (quantity from Component)
                        resultSet.getBigDecimal("taxRate"),        // BigDecimal (taxRate from Component)
                        (UUID) resultSet.getObject("projectId"),    // UUID (projectId from Component)
                        resultSet.getBigDecimal("qualityCoefficient"), // BigDecimal
                        resultSet.getBigDecimal("transportCost")    // BigDecimal
                );
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

}
