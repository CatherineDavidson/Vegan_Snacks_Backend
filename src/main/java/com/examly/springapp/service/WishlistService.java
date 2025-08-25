package com.examly.springapp.service;

import com.examly.springapp.model.User;
import com.examly.springapp.model.product.Product;
import com.examly.springapp.model.*;
import com.examly.springapp.repository.WishlistItemRepository;
import com.examly.springapp.repository.WishlistRepository;
import com.examly.springapp.service.product.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository itemRepository;
    private final ProductService productService;

    @Transactional
    public Wishlist getOrCreate(User user) {
        return wishlistRepository.findByUser(user).orElseGet(() -> {
            Wishlist w = new Wishlist();
            w.setUser(user);
            return wishlistRepository.save(w);
        });
    }

    @Transactional
    public Wishlist addItem(User user, Long productId) {
        Wishlist wishlist = getOrCreate(user);
        Product product = productService.get(productId);

        boolean exists = wishlist.getItems().stream()
                .anyMatch(item -> item.getProduct().getProductId().equals(productId));

        if (!exists) {
            WishlistItem item = new WishlistItem();
            item.setWishlist(wishlist);
            item.setProduct(product);
            itemRepository.save(item);
            wishlist.getItems().add(item);
        }

        return wishlist;
    }

    @Transactional
    public Wishlist removeItem(User user, Long wishlistItemId) {
        Wishlist wishlist = getOrCreate(user);
        wishlist.getItems().removeIf(i -> {
            if (i.getWishlistItemId().equals(wishlistItemId)) {
                itemRepository.deleteById(wishlistItemId);
                return true;
            }
            return false;
        });
        return wishlist;
    }

    @Transactional
    public Wishlist clear(User user) {
        Wishlist wishlist = getOrCreate(user);
        wishlist.getItems().forEach(i -> itemRepository.deleteById(i.getWishlistItemId()));
        wishlist.getItems().clear();
        return wishlist;
    }
}
