package com.examly.springapp.dto.product;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReviewDTO {
    private Long productId;
    private Integer rating; // 1..5
    private String comment;
}
