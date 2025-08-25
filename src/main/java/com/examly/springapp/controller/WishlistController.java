package com.examly.springapp.controller;

import com.examly.springapp.model.User;
import com.examly.springapp.model.Wishlist;
import com.examly.springapp.service.WishlistService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    // Assuming you have a way to fetch current logged-in User
    private User getCurrentUser() {
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping
    public Wishlist getWishlist() {
        return wishlistService.getOrCreate(getCurrentUser());
    }

    @PostMapping("/{productId}")
    public Wishlist addToWishlist(@PathVariable Long productId) {
        return wishlistService.addItem(getCurrentUser(), productId);
    }

    @DeleteMapping("/items/{wishlistItemId}")
    public Wishlist removeFromWishlist(@PathVariable Long wishlistItemId) {
        return wishlistService.removeItem(getCurrentUser(), wishlistItemId);
    }

    @DeleteMapping
    public Wishlist clearWishlist() {
        return wishlistService.clear(getCurrentUser());
    }
}
