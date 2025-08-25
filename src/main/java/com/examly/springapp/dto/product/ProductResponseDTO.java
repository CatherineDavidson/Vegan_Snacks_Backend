package com.examly.springapp.dto.product;

import com.examly.springapp.model.product.Product;

public record ProductResponseDTO(
        Long productId,
        String name,
        String description,
        String ingredients,
        String nutritionInfo,
        String allergens,
        Double weight,
        String packageSize,
        Integer shelfLife,
        String storageInstructions,
        double price,
        String imageUrl,
        String status,
        Integer stock,
        String vendorName,
        String categoryName
) {
    public static ProductResponseDTO fromEntity(Product product) {
        return new ProductResponseDTO(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getIngredients(),
                product.getNutritionInfo(),
                product.getAllergens(),
                product.getWeight(),
                product.getPackageSize(),
                product.getShelfLife(),
                product.getStorageInstructions(),
                product.getPrice(),
                product.getImageUrl(),
                product.getStatus() != null ? product.getStatus().name() : null,
                product.getStock() != null ? product.getStock() : 0,
                product.getVendor() != null ? product.getVendor().getUser().getUserName() : "N/A", 
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }
}
