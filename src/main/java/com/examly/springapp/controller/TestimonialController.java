package com.examly.springapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.TestimonialResponseDTO;
import com.examly.springapp.model.Testimonial;
import com.examly.springapp.service.TestimonialService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/testimonial/")
public class TestimonialController {
    private final TestimonialService testimonialService;

    @GetMapping("/all")
    public List<Testimonial> getAllTestimonial(){
        return testimonialService.AllTestimonials();
    }
    
    @GetMapping
    public List<Testimonial> getTestimonialsOfUser(@RequestParam Long id){
        return testimonialService.TestimoinalsOfUser(id);
    }

    @PostMapping
    public ResponseEntity<String> createTestimonial(@RequestBody Testimonial testimonial){
        return ResponseEntity.ok(testimonialService.saveTestimonial(testimonial));
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTestimonial(@PathVariable Long id, @RequestBody Testimonial testimonial){
        return ResponseEntity.ok(testimonialService.updateTestimonial(id, testimonial));
    }

    @GetMapping("/top")
    public ResponseEntity<List<TestimonialResponseDTO>> getTopTestimonials() {
        return ResponseEntity.ok(testimonialService.getTopTestimonials());
    }
}
