package com.BatiCuisine.util;

import java.math.BigDecimal;
import java.util.List;
import com.BatiCuisine.model.Material;
import com.BatiCuisine.model.Labor;

public class CostCalculator {

    // Calculate VAT
    public static BigDecimal calculateVat(BigDecimal totalCost, BigDecimal vatRate) {
        if (vatRate == null || vatRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le taux de TVA doit être supérieur ou égal à zéro.");
        }
        return totalCost.multiply(vatRate).divide(BigDecimal.valueOf(100));
    }

    // Calculate Profit Margin
    public static BigDecimal calculateProfit(BigDecimal totalCost, BigDecimal profitMargin) {
        if (profitMargin == null || profitMargin.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La marge bénéficiaire doit être supérieure ou égale à zéro.");
        }
        return totalCost.multiply(profitMargin).divide(BigDecimal.valueOf(100));
    }

    // Calculate Total Material Cost
    public static BigDecimal calculateTotalMaterialCost(List<Material> materials) {
        return materials.stream()
                .map(material -> material.getUnitCost().multiply(material.getQuantity()).add(material.getTransportCost()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Calculate Total Labor Cost
    public static BigDecimal calculateTotalLaborCost(List<Labor> labors) {
        return labors.stream()
                .map(labor -> labor.getHourlyRate().multiply(labor.getHoursWorked()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Calculate Total Project Cost
    public static BigDecimal calculateTotalProjectCost(BigDecimal materialCost, BigDecimal laborCost, BigDecimal vatRate, BigDecimal profitMargin) {
        BigDecimal totalBeforeVat = materialCost.add(laborCost);
        BigDecimal vatAmount = calculateVat(totalBeforeVat, vatRate);
        BigDecimal profitAmount = calculateProfit(totalBeforeVat, profitMargin);
        return totalBeforeVat.add(vatAmount).add(profitAmount);
    }
}
