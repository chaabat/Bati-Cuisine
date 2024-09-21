package com.BatiCuisine.service;

import com.BatiCuisine.model.Labor;
import com.BatiCuisine.repository.interfaces.LaborRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class LaborService {
    private final LaborRepository laborRepository;

    public LaborService(LaborRepository laborRepository) {
        this.laborRepository = laborRepository;
    }

    public void addLabor(Labor labor) {
        if (labor == null) {
            throw new IllegalArgumentException("Labor cannot be null");
        }
        laborRepository.addLabor(labor);
    }
    public List<Labor> findByProjectId(UUID projectId) {
        return laborRepository.findByProjectId(projectId);
    }


    public List<Labor> listLabor() {
        return laborRepository.getAllLabors();
    }

    public BigDecimal calculateTotalLaborCost(List<Labor> labors) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Labor labor : labors) {
            if (labor.getHourlyRate() != null && labor.getHoursWorked() != null) {
                totalCost = totalCost.add(labor.getHourlyRate().multiply(labor.getHoursWorked()));
            }
        }
        return totalCost;
    }
}
