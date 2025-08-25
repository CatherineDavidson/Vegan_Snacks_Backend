package com.examly.springapp.repository.product;

import com.examly.springapp.model.product.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findByProductProductId(Long productId);
}
