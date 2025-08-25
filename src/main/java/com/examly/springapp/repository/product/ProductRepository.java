package com.examly.springapp.repository.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.enums.ProductStatus;
import com.examly.springapp.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findByVendorVendorId(Long vendorId);

    List<Product> findByStatus(ProductStatus approved);
       // Search by name
    List<Product> findByNameContainingIgnoreCase(String name);

    // Filter by category
    List<Product> findByCategory_NameIgnoreCase(String category);

    // Search + category
    List<Product> findByNameContainingIgnoreCaseAndCategory_NameIgnoreCase(String name, String category);

    Page<Product> findByNameContainingIgnoreCaseAndCategory_NameIgnoreCase(String q, String category,
            Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String q, Pageable pageable);

    Page<Product> findByCategory_NameIgnoreCase(String category, Pageable pageable);
}