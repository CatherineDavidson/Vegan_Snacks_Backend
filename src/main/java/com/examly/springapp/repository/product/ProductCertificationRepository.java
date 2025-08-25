package com.examly.springapp.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.product.ProductCertification;

public interface ProductCertificationRepository extends JpaRepository<ProductCertification, Long>{

    List<ProductCertification> findByProductProductId(Long productId);
    
}
