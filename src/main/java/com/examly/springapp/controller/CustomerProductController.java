package com.examly.springapp.controller;

import com.examly.springapp.dto.product.CartItemDTO;
import com.examly.springapp.dto.product.ProductResponseDTO;
import com.examly.springapp.dto.product.ReviewDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.model.product.CustomerCart;
import com.examly.springapp.model.product.Product;
import com.examly.springapp.model.product.ProductReview;
import com.examly.springapp.service.product.CartService;
import com.examly.springapp.service.product.ProductService;
import com.examly.springapp.service.product.ReviewService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class CustomerProductController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final CartService cartService;

    // Reviews
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('VENDOR') or hasRole('ADMIN')")
    @PostMapping("/reviews")
    public ResponseEntity<ProductReview> addReview(@RequestBody ReviewDTO dto, Authentication auth) {
        User u = (User) auth.getPrincipal();
        return ResponseEntity.ok(reviewService.add(u, dto));
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<List<ProductReview>> listReviews(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.list(productId));
    }

    // Cart
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/cart")
    public ResponseEntity<CustomerCart> getCart(Authentication auth) {
        User u = (User) auth.getPrincipal();
        return ResponseEntity.ok(cartService.getOrCreate(u));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/cart/items")
    public ResponseEntity<CustomerCart> addToCart(@RequestBody CartItemDTO dto, Authentication auth) {
        User u = (User) auth.getPrincipal();
        return ResponseEntity.ok(cartService.addItem(u, dto));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/cart/items/{cartItemId}")
    public ResponseEntity<CustomerCart> removeFromCart(@PathVariable Long cartItemId, Authentication auth) {
        User u = (User) auth.getPrincipal();
        return ResponseEntity.ok(cartService.removeItem(u, cartItemId));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/cart")
    public ResponseEntity<CustomerCart> clearCart(Authentication auth) {
        User u = (User) auth.getPrincipal();
        return ResponseEntity.ok(cartService.clear(u));
    }

     @GetMapping("/all")
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/approved")
    public List<ProductResponseDTO> getApprovedProducts() {
        return productService.listApproved();
    }

        @GetMapping("/all/page")
    public Page<Product> listProducts(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            Pageable pageable) {
        return productService.getAll(q, category, pageable);
    }

    
}