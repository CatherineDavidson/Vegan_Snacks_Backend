package com.examly.springapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private String productName;
    private int quantity;
    private double priceAtPurchase;

    public OrderItemDTO(String productName, int quantity, double priceAtPurchase) {
        this.productName = productName;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

}
