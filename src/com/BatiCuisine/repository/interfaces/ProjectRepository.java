package com.BatiCuisine.repository.interfaces;

import com.BatiCuisine.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository {
    void addProject(Project project);   // Method to add a new project
    Optional<Project> getProjectById(UUID id);   // Method to get a project by its UUID
    List<Project> getAllProjects();   // Method to retrieve all projects
}
