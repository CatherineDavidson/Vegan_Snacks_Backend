package com.examly.springapp.model.product;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.examly.springapp.enums.ProductStatus;
import com.examly.springapp.model.Category;
import com.examly.springapp.model.vendor.Vendor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    @JsonIgnore
    private Vendor vendor;

    private String name;
    private String description;
    private String ingredients;
    private String nutritionInfo;
    private String allergens;
    private Double weight;
    private String packageSize;
    private Integer shelfLife; // in days
    private String storageInstructions;
    private Integer stock;
    private double price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status ; 
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(length = 500)
    private String imageUrl;


   @ManyToOne
@JoinColumn(name = "category_id")
    @JsonBackReference("category-product")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
private Category category;

}
