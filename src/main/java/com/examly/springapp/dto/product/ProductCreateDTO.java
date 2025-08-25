// package com.examly.springapp.dto.product;

// import lombok.*;

// @Getter @Setter @NoArgsConstructor @AllArgsConstructor
// public class ProductCreateDTO {
//     private Long vendorId;
//     private String name;
//     private String description;
//     private String ingredients;
//     private String nutritionInfo;
//     private String allergens;
//     private Double weight;
//     private String packageSize;
//     private Integer shelfLife;
//     private String storageInstructions;
//     private String imageUrl; 
// }

// ProductCreateDTO.java
package com.examly.springapp.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.examly.springapp.model.product.Product;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductCreateDTO {
    private Long vendorId;
    private Long categoryId;
    private String categoryName;
    private String name;
    private String description;
    private String ingredients;
    private String nutritionInfo;
    private String allergens;
    private Double weight;
    private String packageSize;
    private Integer shelfLife;
    private String storageInstructions;
    private double price;
    private Integer stock;
    private String imageUrl;

    public ProductCreateDTO(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        if (product.getCategory() != null) {
            this.categoryName = product.getCategory().getName();
        }
    }
}