package com.examly.springapp.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.product.ProductInventory;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {

    List<ProductInventory> findByProductProductId(Long productId);
    
}
