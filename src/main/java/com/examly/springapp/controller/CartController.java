package com.examly.springapp.controller;

import com.examly.springapp.dto.product.CartItemDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.model.product.CustomerCart;
import com.examly.springapp.service.product.CartService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private User getCurrentUser() {
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping
    public CustomerCart getCart() {
        return cartService.getCart(getCurrentUser());
    }

    @PostMapping("/items")
    public CustomerCart addItem(@RequestBody CartItemDTO item) {
        return cartService.addItem(getCurrentUser(), item);
    }

    @PutMapping("/items/{itemId}")
    public CustomerCart updateItem(@PathVariable Long itemId, @RequestParam int quantity) {
        return cartService.updateItem(getCurrentUser(), itemId, quantity);
    }

    @DeleteMapping("/items/{itemId}")
    public CustomerCart removeItem(@PathVariable Long itemId) {
        return cartService.removeItem(getCurrentUser(), itemId);
    }

    @DeleteMapping
    public CustomerCart clearCart() {
        return cartService.clear(getCurrentUser());
    }
}
