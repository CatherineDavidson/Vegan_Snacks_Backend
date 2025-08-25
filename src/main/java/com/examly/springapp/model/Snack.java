package com.examly.springapp.model;

import com.examly.springapp.model.vendor.Vendor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Snack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Min(value = 0, message = "Price must be positive")
    private double price;

    private int quantity;

    @Min(value = 0, message = "Expiry in months must be positive")
    private int expiryInMonths;

    //================== Relationships =================//

    @JsonBackReference("category-snacks")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonBackReference
    @ManyToOne   
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
}
