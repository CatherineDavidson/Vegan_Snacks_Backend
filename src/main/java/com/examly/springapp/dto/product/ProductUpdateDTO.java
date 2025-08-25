// package com.examly.springapp.dto.product;

// import lombok.*;

// @Getter @Setter @NoArgsConstructor @AllArgsConstructor
// public class ProductUpdateDTO {
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

// ProductUpdateDTO.java
package com.examly.springapp.dto.product;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductUpdateDTO {
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
    
}