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
    public List<Material> findByProjectId(UUID projectId) {
        List<Material> materials = new ArrayList<>();
        String query = "SELECT c.id AS componentId, c.name, c.unitCost, c.quantity, c.taxRate, c.projectId, m.qualityCoefficient, m.transportCost " +
                "FROM component c JOIN materials m ON c.id = m.componentId WHERE c.projectId = ?";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, projectId, java.sql.Types.OTHER);
            ResultSet resultSet = statement.executeQuery();

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

}
