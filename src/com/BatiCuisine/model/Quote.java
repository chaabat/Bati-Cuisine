package com.BatiCuisine.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Quote {
    private UUID id;                   // Changed to UUID
    private BigDecimal estimatedAmount;
    private LocalDate issueDate;
    private LocalDate validityDate;
    private boolean isAccepted;
    private Project project;

    // Constructor for creating a new Quote (without ID)
    public Quote(BigDecimal estimatedAmount, LocalDate issueDate, LocalDate validityDate, boolean isAccepted, Project project) {
        this.id = UUID.randomUUID();
        this.estimatedAmount = estimatedAmount;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.isAccepted = isAccepted;
        this.project = project;
    }

    // Constructor for initializing a Quote from the database (with ID)
    public Quote(UUID id, BigDecimal estimatedAmount, LocalDate issueDate, LocalDate validityDate, boolean isAccepted, Project project) {
        this.id = id;
        this.estimatedAmount = estimatedAmount;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.isAccepted = isAccepted;
        this.project = project;
    }

    // Getters & Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(BigDecimal estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
