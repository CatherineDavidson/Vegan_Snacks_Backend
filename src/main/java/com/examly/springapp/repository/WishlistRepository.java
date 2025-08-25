package com.examly.springapp.repository;

import com.examly.springapp.model.User;
import com.examly.springapp.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUser(User user);
}
