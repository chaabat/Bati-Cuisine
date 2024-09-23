package com.BatiCuisine.repository.implementation;

import com.BatiCuisine.model.Project;
import com.BatiCuisine.model.Client;
import com.BatiCuisine.model.ProjectStatus;
import com.BatiCuisine.repository.interfaces.ProjectRepository;
import com.BatiCuisine.config.DataBaseConnection;

import java.sql.*;
import java.util.*;

public class ProjectRepositoryImpl implements ProjectRepository {
    private final Map<UUID, Project> projectCache = new HashMap<>();

    @Override
    public void addProject(Project project) {
        String query = "INSERT INTO projects (id, projectName, profitMargin, totalCost, status, clientId, surface, type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            UUID projectId = project.getId() != null ? project.getId() : UUID.randomUUID();
            project.setId(projectId);

            statement.setObject(1, projectId, Types.OTHER);
            statement.setString(2, project.getProjectName());
            statement.setBigDecimal(3, project.getProjectMargin());
            statement.setBigDecimal(4, project.getTotalCost());
            statement.setObject(5, project.getProjectStatus().name(), Types.OTHER);
            statement.setObject(6, project.getClient().getId(), Types.OTHER);
            statement.setBigDecimal(7, project.getSurface());
            statement.setString(8, project.getType());

            statement.executeUpdate();
            projectCache.put(projectId, project);

        } catch (SQLException e) {
            System.err.println("Error adding project to the database. Details: " + e.getMessage());
        }
    }

    @Override
    public Optional<Project> getProjectById(UUID id) {
        if (projectCache.containsKey(id)) {
            return Optional.of(projectCache.get(id));
        }

        String query = "SELECT p.*, c.name AS clientName, c.address AS clientAddress, c.phone AS clientPhone, c.isProfessional " +
                "FROM projects p JOIN clients c ON p.clientId = c.id WHERE p.id = ?";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, id, Types.OTHER);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Project project = new Project(
                        (UUID) resultSet.getObject("id"),
                        resultSet.getString("projectName"),
                        resultSet.getBigDecimal("profitMargin"),
                        ProjectStatus.valueOf(resultSet.getString("status")),
                        resultSet.getBigDecimal("totalCost"),
                        new Client(
                                (UUID) resultSet.getObject("clientId"),
                                resultSet.getString("clientName"),
                                resultSet.getString("clientAddress"),
                                resultSet.getString("clientPhone"),
                                resultSet.getBoolean("isProfessional")
                        ),
                        resultSet.getBigDecimal("surface"),
                        resultSet.getString("type")
                );
                projectCache.put(project.getId(), project);
                return Optional.of(project);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching project by ID. Details: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT p.*, c.name AS clientName, c.address AS clientAddress, c.phone AS clientPhone, c.isProfessional " +
                "FROM projects p JOIN clients c ON p.clientId = c.id";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Project project = new Project(
                        (UUID) resultSet.getObject("id"),
                        resultSet.getString("projectName"),
                        resultSet.getBigDecimal("profitMargin"),
                        ProjectStatus.valueOf(resultSet.getString("status")),
                        resultSet.getBigDecimal("totalCost"),
                        new Client(
                                (UUID) resultSet.getObject("clientId"),
                                resultSet.getString("clientName"),
                                resultSet.getString("clientAddress"),
                                resultSet.getString("clientPhone"),
                                resultSet.getBoolean("isProfessional")
                        ),
                        resultSet.getBigDecimal("surface"),
                        resultSet.getString("type")
                );
                projectCache.put(project.getId(), project);
                projects.add(project);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all projects from the database. Details: " + e.getMessage());
        }
        return projects;
    }

    @Override
    public void updateProject(Project project) {
        String query = "UPDATE projects SET projectName = ?, profitMargin = ?, totalCost = ?, status = ?::project_status, surface = ?, type = ? WHERE id = ?";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, project.getProjectName());
            statement.setBigDecimal(2, project.getProjectMargin());
            statement.setBigDecimal(3, project.getTotalCost());
            statement.setString(4, project.getProjectStatus().name());
            statement.setBigDecimal(5, project.getSurface());
            statement.setString(6, project.getType());
            statement.setObject(7, project.getId(), Types.OTHER);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating project: " + e.getMessage());
        }
    }
    public void updateProjectTotalCost(Project project) {
        String query = "UPDATE projects SET totalCost = ? WHERE id = ?";

        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setBigDecimal(1, project.getTotalCost());
            statement.setObject(2, project.getId(), Types.OTHER);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating project total cost: " + e.getMessage());
        }
    }


    @Override
    public Integer getClientProjectsCount(UUID clientId) throws SQLException {
        String query = "SELECT COUNT(*) FROM projects WHERE clientId = ?";
        try (Connection connection = DataBaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setObject(1, clientId, Types.OTHER);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;  // Return 0 if no projects found
    }
}
