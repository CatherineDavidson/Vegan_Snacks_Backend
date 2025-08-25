package com.examly.springapp.model;

import java.sql.Timestamp;
import java.util.List;

import com.examly.springapp.model.product.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String description;
    private Integer noOfProducts;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @JsonManagedReference("category-snacks")
    @OneToMany(mappedBy = "category")
    private List<Snack> snacks;

    @JsonManagedReference("category-product")
    @OneToMany(mappedBy = "category")
    private List<Product> products;

}
