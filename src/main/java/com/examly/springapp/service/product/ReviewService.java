package com.examly.springapp.service.product;

import com.examly.springapp.dto.product.ReviewDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.model.product.Product;
import com.examly.springapp.model.product.ProductReview;
import com.examly.springapp.repository.product.ProductReviewRepository;
import com.examly.springapp.service.product.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService{

    private final ProductReviewRepository reviewRepository;
    private final ProductService productService;

    @Transactional
    public ProductReview add(User user, ReviewDTO dto) {
        Product p = productService.get(dto.getProductId());
        ProductReview r = new ProductReview();
        r.setProduct(p);
        r.setUser(user);
        r.setRating(dto.getRating());
        r.setComment(dto.getComment());
        return reviewRepository.save(r);
    }

    @Transactional(readOnly = true)
    public List<ProductReview> list(Long productId) {
        return reviewRepository.findByProductProductId(productId);
    }
}
