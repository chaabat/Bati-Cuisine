package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Labor;
import com.BatiCuisine.repository.interfaces.LaborRepository;
import com.BatiCuisine.config.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LaborRepositoryImpl implements LaborRepository {

    @Override
    public void addLabor(Labor labor) {
        String query = "INSERT INTO labor (componentId, hourlyRate, hoursWorked, productivityFactor) VALUES (uuid_generate_v4(), ?, ?, ?)";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBigDecimal(1, labor.getHourlyRate());      // Use BigDecimal for precision
            statement.setBigDecimal(2, labor.getHoursWorked());     // Use BigDecimal for precision
            statement.setBigDecimal(3, labor.getProductivityFactor()); // Use BigDecimal for precision

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Labor> getLaborById(UUID id) {
        String query = "SELECT * FROM labor WHERE componentId = ?";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, java.sql.Types.OTHER);  // Use UUID as parameter

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Labor labor = new Labor(
                        (UUID) resultSet.getObject("componentId"),  // UUID
                        resultSet.getBigDecimal("hourlyRate"),      // BigDecimal
                        resultSet.getBigDecimal("hoursWorked"),     // BigDecimal
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
        String query = "SELECT * FROM labor";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Labor labor = new Labor(
                        (UUID) resultSet.getObject("componentId"),  // UUID
                        resultSet.getBigDecimal("hourlyRate"),      // BigDecimal
                        resultSet.getBigDecimal("hoursWorked"),     // BigDecimal
                        resultSet.getBigDecimal("productivityFactor") // BigDecimal
                );
                laborList.add(labor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laborList;
    }




}
