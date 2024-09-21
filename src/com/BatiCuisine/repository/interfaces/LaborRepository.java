package com.BatiCuisine.repository.interfaces;

import com.BatiCuisine.model.Labor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LaborRepository {
    void addLabor(Labor labor);
    Optional<Labor> getLaborById(UUID id);
    List<Labor> getAllLabors();
    List<Labor> findByProjectId(UUID projectId);
}
