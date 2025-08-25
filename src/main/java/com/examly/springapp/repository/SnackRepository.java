package com.examly.springapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.examly.springapp.model.Snack;

public interface SnackRepository extends JpaRepository<Snack, Long>{
    @Query("SELECT s FROM Snack s WHERE s.category.name = :name")
    Page<Snack> findAllSnacksByCategory(Pageable pageable, @Param("name") String name);

}
