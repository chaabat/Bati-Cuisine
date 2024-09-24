package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Labor;
import com.BatiCuisine.repository.interfaces.LaborRepository;
import com.BatiCuisine.config.DataBaseConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

public class LaborRepositoryImpl implements LaborRepository {

    @Override
    public void addLabor(Labor labor) {
        String insertComponentQuery = "INSERT INTO component (name, unitCost, quantity, componentType, taxRate, projectId) VALUES (?, ?, ?, 'Labor', ?, ?) RETURNING id";
        String insertLaborQuery = "INSERT INTO labor (componentId, hourlyRate, hoursWorked, productivityFactor) VALUES (?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement insertComponentStmt = connection.prepareStatement(insertComponentQuery);
             PreparedStatement insertLaborStmt = connection.prepareStatement(insertLaborQuery)) {

            insertComponentStmt.setString(1, labor.getName());
            insertComponentStmt.setBigDecimal(2, labor.getUnitCost());
            insertComponentStmt.setBigDecimal(3, BigDecimal.ZERO);
            insertComponentStmt.setBigDecimal(4, labor.getTaxRate());
            insertComponentStmt.setObject(5, labor.getProjectId(), java.sql.Types.OTHER);

            ResultSet resultSet = insertComponentStmt.executeQuery();
            if (resultSet.next()) {
                UUID componentId = (UUID) resultSet.getObject("id");

                insertLaborStmt.setObject(1, componentId, java.sql.Types.OTHER);
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
    public List<Labor> findByProjectId(UUID projectId) {
        List<Labor> laborList = new ArrayList<>();
        String query = "SELECT c.id AS componentId, c.name, c.unitCost, c.taxRate, c.projectId, l.hourlyRate, l.hoursWorked, l.productivityFactor " +
                "FROM labor l JOIN component c ON l.componentId = c.id WHERE c.projectId = ?";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, projectId, java.sql.Types.OTHER);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Labor labor = new Labor(
                        (UUID) resultSet.getObject("componentId"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("unitCost"),
                        BigDecimal.ZERO,
                        resultSet.getBigDecimal("taxRate"),
                        (UUID) resultSet.getObject("projectId"),
                        resultSet.getBigDecimal("hourlyRate"),
                        resultSet.getBigDecimal("hoursWorked"),
                        resultSet.getBigDecimal("productivityFactor")
                );
                laborList.add(labor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laborList;
    }

}
