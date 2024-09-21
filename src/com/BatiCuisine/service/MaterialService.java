package com.BatiCuisine.service;

import com.BatiCuisine.model.Material;
import com.BatiCuisine.repository.interfaces.MaterialRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MaterialService {
    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public void addMaterial(Material material) {
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }
        materialRepository.addMaterial(material);
    }

    public List<Material> findByProjectId(UUID projectId) {
        return materialRepository.findByProjectId(projectId);
    }


    public List<Material> listMaterials() {
        return materialRepository.getAllMaterials();
    }

    public BigDecimal calculateTotalMaterialCost(List<Material> materials) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Material material : materials) {
            if (material.getUnitCost() != null && material.getQuantity() != null) {
                totalCost = totalCost.add(material.getUnitCost().multiply(material.getQuantity()));
            }
        }
        return totalCost;
    }
}
