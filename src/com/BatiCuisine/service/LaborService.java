package com.BatiCuisine.service;

import com.BatiCuisine.model.Labor;
import com.BatiCuisine.repository.interfaces.LaborRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LaborService {
    private final LaborRepository laborRepository;

    // Constructor to inject LaborRepository dependency
    public LaborService(LaborRepository laborRepository) {
        if (laborRepository == null) {
            throw new IllegalArgumentException("LaborRepository cannot be null");
        }
        this.laborRepository = laborRepository;
    }

    // Add a new labor record (delegates to the repository)
    public void addLabor(Labor labor) {
        if (labor == null) {
            throw new IllegalArgumentException("Labor cannot be null");
        }
        laborRepository.addLabor(labor);
    }

    // Get labor by UUID (returns Optional to handle the case where labor might not exist)
    public Optional<Labor> getLaborById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        return laborRepository.getLaborById(id);
    }

    // Get all labor records (returns a List of labor)
    public List<Labor> getAllLabors() {
        return laborRepository.getAllLabors();
    }

    // Calculate total labor cost based on a list of labor records
    public BigDecimal calculateTotalLaborCost(List<Labor> labors) {
        if (labors == null) {
            throw new IllegalArgumentException("Labor list cannot be null");
        }
        return labors.stream()
                .map(labor -> labor.getHourlyRate().multiply(labor.getHoursWorked())) // Remove BigDecimal.valueOf
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
