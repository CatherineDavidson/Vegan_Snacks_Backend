package com.examly.springapp.dto.product;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CartItemDTO {
    private Long productId;
    private Integer quantity;
}
