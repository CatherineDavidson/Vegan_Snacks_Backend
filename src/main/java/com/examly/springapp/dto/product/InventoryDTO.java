package com.examly.springapp.dto.product;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InventoryDTO {
    private Integer quantity;
    private LocalDate expiryDate;
    private String batchNumber;
}
