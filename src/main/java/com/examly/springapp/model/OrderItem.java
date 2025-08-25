package com.examly.springapp.model;

import com.examly.springapp.model.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Product product;

    private int quantity;

    private double priceAtPurchase; // snapshot of price

    @ManyToOne(optional = false)
    @JsonBackReference("order-items")
    private Orders order;
}
