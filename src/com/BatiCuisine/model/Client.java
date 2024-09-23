package com.BatiCuisine.model;

import java.util.UUID;

public class Client {

    private UUID id;
    private String name;
    private String address;
    private String phone;
    private boolean isProfessional;

    // Constructor when fetched from database
    public Client(UUID id, String name, String address, String phone, boolean isProfessional) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isProfessional = isProfessional;
    }

    // Constructor  for new clients
    public Client(String name, String address, String phone, boolean isProfessional) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isProfessional = isProfessional;
    }

    // Getters & Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }


    public String getPhone() {
        return phone;
    }


    public boolean isProfessional() {
        return isProfessional;
    }


}