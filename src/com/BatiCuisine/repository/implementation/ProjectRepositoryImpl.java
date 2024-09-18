package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Project;
import com.BatiCuisine.model.Client;
import com.BatiCuisine.model.ProjectStatus;
import com.BatiCuisine.repository.interfaces.ProjectRepository;
import com.BatiCuisine.config.DataBaseConnection;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectRepositoryImpl implements ProjectRepository {

    @Override
    public void addProject(Project project) {
        String query = "INSERT INTO projects (id, projectName, profitMargin, totalCost, status, clientId) VALUES (uuid_generate_v4(), ?, ?, ?, ?, ?)";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, project.getProjectName());
            statement.setBigDecimal(2, project.getProjectMargin());
            statement.setBigDecimal(3, project.getTotalCost());
            statement.setString(4, project.getProjectStatus().name());  // Enum as String
            statement.setObject(5, project.getClient().getId(), java.sql.Types.OTHER);  // Client UUID

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Project> getProjectById(UUID id) {
        String query = "SELECT * FROM projects WHERE id = ?";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id, java.sql.Types.OTHER);  // Set UUID as parameter

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Project project = new Project(
                        (UUID) resultSet.getObject("id"),  // UUID
                        resultSet.getString("projectName"),
                        resultSet.getBigDecimal("profitMargin"),  // BigDecimal
                        ProjectStatus.valueOf(resultSet.getString("status")),  // Enum status
                        resultSet.getBigDecimal("totalCost"),  // BigDecimal
                        new Client(  // Build Client object from resultSet
                                (UUID) resultSet.getObject("clientId"),
                                resultSet.getString("clientName"),
                                resultSet.getString("clientAddress"),
                                resultSet.getString("clientPhone"),
                                resultSet.getBoolean("isProfessional")
                        )
                );
                return Optional.of(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM projects p INNER JOIN clients c ON p.clientId = c.id";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Project project = new Project(
                        (UUID) resultSet.getObject("id"),  // UUID
                        resultSet.getString("projectName"),
                        resultSet.getBigDecimal("profitMargin"),  // BigDecimal
                        ProjectStatus.valueOf(resultSet.getString("status")),  // Enum status
                        resultSet.getBigDecimal("totalCost"),  // BigDecimal
                        new Client(  // Build Client object from resultSet
                                (UUID) resultSet.getObject("clientId"),
                                resultSet.getString("name"),
                                resultSet.getString("address"),
                                resultSet.getString("phone"),
                                resultSet.getBoolean("isProfessional")
                        )
                );
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }
}
