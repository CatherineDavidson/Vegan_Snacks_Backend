package com.examly.springapp.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String userName;
    private String email;
    private List<OrderItemDTO> items;
    private double totalAmount;
    private LocalDateTime orderDate;
}
