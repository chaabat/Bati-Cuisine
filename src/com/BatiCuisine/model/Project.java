package com.BatiCuisine.model;

public class Project {

    private int id;
    private String projectName;
    private double projectMargin;
    private double totalCost;
    private String projectStatus;
    private Client client;

    // Constructor

    public Project(int id, String projectName, double projectMargin, String projectStatus, double totalCost, Client client) {
        this.id = id;
        this.projectName = projectName;
        this.projectMargin = projectMargin;
        this.projectStatus = projectStatus;
        this.totalCost = totalCost;
        this.client = client;
    }

    //Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getProjectMargin() {
        return projectMargin;
    }

    public void setProjectMargin(double projectMargin) {
        this.projectMargin = projectMargin;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
