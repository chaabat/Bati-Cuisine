package com.BatiCuisine.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
                .map(material -> material.getUnitCost()
                        .multiply(material.getQuantity())
                        .add(material.getTransportCost()))  // Include transport cost in material total
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Calculate Total Labor Cost
    public static BigDecimal calculateTotalLaborCost(List<Labor> labors) {
        return labors.stream()
                .map(labor -> labor.getHourlyRate()
                        .multiply(labor.getHoursWorked())
                        .multiply(labor.getProductivityFactor()))  // Adjust for productivity
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Calculate Total Project Cost (with VAT and Profit Margin)
    public static BigDecimal calculateTotalProjectCost(BigDecimal materialCost, BigDecimal laborCost, BigDecimal vatRate, BigDecimal profitMargin) {
        BigDecimal totalBeforeVat = materialCost.add(laborCost);
        BigDecimal vatAmount = calculateVat(totalBeforeVat, vatRate);
        BigDecimal profitAmount = calculateProfit(totalBeforeVat, profitMargin);
        return totalBeforeVat.add(vatAmount).add(profitAmount);
    }

    // Calculate Total Cost with Discount for Professional Clients
    public static BigDecimal totalCostWithDiscount(BigDecimal price, int projects, boolean isProfessional) {
        BigDecimal discountRate = getDiscountRate(projects, isProfessional);
        return price.multiply(discountRate).setScale(2, RoundingMode.HALF_UP); // Apply discount rate
    }

    // Determine Discount Rate Based on Number of Projects and Client Type
    private static BigDecimal getDiscountRate(int projects, boolean isProfessional) {
        if (projects < 0) {
            throw new IllegalArgumentException("Le nombre de projets ne peut pas être négatif.");
        }

        // Different discount rates for professionals and non-professionals
        switch (projects) {
            case 3:
                return isProfessional ? BigDecimal.valueOf(0.90) : BigDecimal.valueOf(0.98);  // 10% for pros, 2% for others
            case 5:
                return isProfessional ? BigDecimal.valueOf(0.85) : BigDecimal.valueOf(0.94);  // 15% for pros, 6% for others
            case 10:
                return isProfessional ? BigDecimal.valueOf(0.80) : BigDecimal.valueOf(0.90);  // 20% for pros, 10% for others
            default:
                return isProfessional ? BigDecimal.valueOf(0.75) : BigDecimal.valueOf(0.86);  // 25% for pros, 14% for others
        }
    }

}
