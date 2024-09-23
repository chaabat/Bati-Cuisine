package com.BatiCuisine.repository.interfaces;

import com.BatiCuisine.model.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository {
     void addProject(Project project);
    Optional<Project> getProjectById(UUID id);
    List<Project> getAllProjects();
    Integer getClientProjectsCount(UUID clientId) throws SQLException;

    void updateProject(Project project);

    void updateProjectTotalCost(Project project);
}
