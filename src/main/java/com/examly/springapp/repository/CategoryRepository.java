package com.examly.springapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> deleteByName(String name);

    Optional<Category> findByName(String name);

    Page<Category> findByNameContainingIgnoreCase(String search, Pageable pageable);

    Object findTop5ByOrderByCreatedAtDesc();

    Optional<Category> findTopByOrderByNoOfProductsDesc();

    boolean existsByName(String name);
    
}
