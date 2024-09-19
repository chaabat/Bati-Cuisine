package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Labor;
import com.BatiCuisine.repository.interfaces.LaborRepository;
import com.BatiCuisine.config.DataBaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LaborRepositoryImpl implements LaborRepository {

    @Override
    public void addLabor(Labor labor) {
        String insertComponentQuery = "INSERT INTO component (name, unitCost, quantity, componentType, taxRate, projectId) VALUES (?, ?, ?, 'Labor', ?, ?) RETURNING id";
        String insertLaborQuery = "INSERT INTO labor (componentId, hourlyRate, hoursWorked, productivityFactor) VALUES (?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement insertComponentStmt = connection.prepareStatement(insertComponentQuery);
             PreparedStatement insertLaborStmt = connection.prepareStatement(insertLaborQuery)) {

            insertComponentStmt.setString(1, labor.getName()); // Use labor's name
            insertComponentStmt.setBigDecimal(2, labor.getUnitCost());
            insertComponentStmt.setBigDecimal(3, BigDecimal.ZERO); // Quantity not applicable for labor
            insertComponentStmt.setBigDecimal(4, labor.getTaxRate());
            insertComponentStmt.setObject(5, labor.getProjectId(), java.sql.Types.OTHER); // Project ID

            ResultSet resultSet = insertComponentStmt.executeQuery();
            if (resultSet.next()) {
                UUID componentId = (UUID) resultSet.getObject("id");

                insertLaborStmt.setObject(1, componentId, java.sql.Types.OTHER); // Use the generated componentId
                insertLaborStmt.setBigDecimal(2, labor.getHourlyRate());
                insertLaborStmt.setBigDecimal(3, labor.getHoursWorked());
                insertLaborStmt.setBigDecimal(4, labor.getProductivityFactor());

                insertLaborStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Labor> getLaborById(UUID id) {
        String query = "SELECT c.id AS componentId, c.name, c.unitCost, c.taxRate, c.projectId, l.hourlyRate, l.hoursWorked, l.productivityFactor " +
                "FROM labor l JOIN component c ON l.componentId = c.id WHERE l.componentId = ?";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, java.sql.Types.OTHER);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Labor labor = new Labor(
                        (UUID) resultSet.getObject("componentId"), // UUID
                        resultSet.getString("name"),                // String (name from Component)
                        resultSet.getBigDecimal("unitCost"),        // BigDecimal (unitCost from Component)
                        BigDecimal.ZERO,                            // Quantity not applicable for labor
                        resultSet.getBigDecimal("taxRate"),        // BigDecimal (taxRate from Component)
                        (UUID) resultSet.getObject("projectId"),    // UUID (projectId from Component)
                        resultSet.getBigDecimal("hourlyRate"),     // BigDecimal
                        resultSet.getBigDecimal("hoursWorked"),    // BigDecimal
                        resultSet.getBigDecimal("productivityFactor") // BigDecimal
                );
                return Optional.of(labor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Labor> getAllLabors() {
        List<Labor> laborList = new ArrayList<>();
        String query = "SELECT c.id AS componentId, c.name, c.unitCost, c.taxRate, c.projectId, l.hourlyRate, l.hoursWorked, l.productivityFactor " +
                "FROM labor l JOIN component c ON l.componentId = c.id";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Labor labor = new Labor(
                        (UUID) resultSet.getObject("componentId"), // UUID
                        resultSet.getString("name"),                // String (name from Component)
                        resultSet.getBigDecimal("unitCost"),        // BigDecimal (unitCost from Component)
                        BigDecimal.ZERO,                            // Quantity not applicable for labor
                        resultSet.getBigDecimal("taxRate"),        // BigDecimal (taxRate from Component)
                        (UUID) resultSet.getObject("projectId"),    // UUID (projectId from Component)
                        resultSet.getBigDecimal("hourlyRate"),     // BigDecimal
                        resultSet.getBigDecimal("hoursWorked"),    // BigDecimal
                        resultSet.getBigDecimal("productivityFactor") // BigDecimal
                );
                laborList.add(labor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laborList;
    }

    // Update Labor Method
    public void updateLabor(Labor labor) {
        String updateComponentQuery = "UPDATE component SET name = ?, unitCost = ?, taxRate = ?, projectId = ? WHERE id = ?";
        String updateLaborQuery = "UPDATE labor SET hourlyRate = ?, hoursWorked = ?, productivityFactor = ? WHERE componentId = ?";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement updateComponentStmt = connection.prepareStatement(updateComponentQuery);
             PreparedStatement updateLaborStmt = connection.prepareStatement(updateLaborQuery)) {

            updateComponentStmt.setString(1, labor.getName());
            updateComponentStmt.setBigDecimal(2, labor.getUnitCost());
            updateComponentStmt.setBigDecimal(3, labor.getTaxRate());
            updateComponentStmt.setObject(4, labor.getProjectId(), java.sql.Types.OTHER);
            updateComponentStmt.setObject(5, labor.getId(), java.sql.Types.OTHER); // Use labor's component ID

            updateComponentStmt.executeUpdate();

            updateLaborStmt.setBigDecimal(1, labor.getHourlyRate());
            updateLaborStmt.setBigDecimal(2, labor.getHoursWorked());
            updateLaborStmt.setBigDecimal(3, labor.getProductivityFactor());
            updateLaborStmt.setObject(4, labor.getId(), java.sql.Types.OTHER); // Use labor's component ID

            updateLaborStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Labor Method
    public void deleteLabor(UUID id) {
        String deleteLaborQuery = "DELETE FROM labor WHERE componentId = ?";
        String deleteComponentQuery = "DELETE FROM component WHERE id = ?";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement deleteLaborStmt = connection.prepareStatement(deleteLaborQuery);
             PreparedStatement deleteComponentStmt = connection.prepareStatement(deleteComponentQuery)) {

            // Delete from labor first
            deleteLaborStmt.setObject(1, id, java.sql.Types.OTHER);
            deleteLaborStmt.executeUpdate();

            // Then delete from component
            deleteComponentStmt.setObject(1, id, java.sql.Types.OTHER);
            deleteComponentStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
