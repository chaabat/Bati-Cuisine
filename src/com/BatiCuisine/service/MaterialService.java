package com.BatiCuisine.service;

import com.BatiCuisine.model.Material;
import com.BatiCuisine.repository.interfaces.MaterialRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MaterialService {
    private final MaterialRepository materialRepository;  // Inject the repository

    // Constructor to inject MaterialRepository dependency
    public MaterialService(MaterialRepository materialRepository) {
        if (materialRepository == null) {
            throw new IllegalArgumentException("MaterialRepository cannot be null");
        }
        this.materialRepository = materialRepository;
    }

    // Add a new material (delegates to the repository)
    public void addMaterial(Material material) {
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }
        materialRepository.addMaterial(material);
    }

    // Get material by UUID (returns Optional to handle the case where material might not exist)
    public Optional<Material> getMaterialById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        return materialRepository.getMaterialById(id);
    }

    // Get all materials (returns a List of materials)
    public List<Material> getAllMaterials() {
        return materialRepository.getAllMaterials();
    }

    // Calculate total material cost based on a list of materials
    public BigDecimal calculateTotalMaterialCost(List<Material> materials) {
        if (materials == null) {
            throw new IllegalArgumentException("Material list cannot be null");
        }
        return materials.stream()
                .map(material -> {
                    if (material.getUnitCost() == null || material.getQuantity() == null || material.getTransportCost() == null) {
                        throw new IllegalArgumentException("Material's unit cost, quantity, and transport cost cannot be null");
                    }
                    return material.getUnitCost().multiply(material.getQuantity()).add(material.getTransportCost());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

