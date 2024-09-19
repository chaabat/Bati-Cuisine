package com.BatiCuisine.repository.interfaces;

import com.BatiCuisine.model.Material;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MaterialRepository {
    void addMaterial(Material material);
    Optional<Material> getMaterialById(UUID id);
    List<Material> getAllMaterials();
}
