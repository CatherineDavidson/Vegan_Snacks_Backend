package com.examly.springapp.repository.product;

import com.examly.springapp.model.product.CartItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteAllByCart_CartId(Long cartId);
}
