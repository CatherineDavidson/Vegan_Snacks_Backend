package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Testimonial;

public interface TestimonialRepository extends JpaRepository<Testimonial,Long> {

    List<Testimonial> findByUserId(Long id);
    List<Testimonial> findTop4ByOrderByRatingDescLatestUpdateDesc();
}
