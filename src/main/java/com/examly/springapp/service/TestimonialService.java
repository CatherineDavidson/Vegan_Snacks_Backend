package com.examly.springapp.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.TestimonialResponseDTO;
import com.examly.springapp.enums.Role;
import com.examly.springapp.model.Testimonial;
import com.examly.springapp.repository.TestimonialRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestimonialService {
    
    private final TestimonialRepository testimonialRepository;

    public List<Testimonial> TestimoinalsOfUser(Long id) {
        return testimonialRepository.findByUserId(id);
    }

    public List<Testimonial> AllTestimonials() {
        return testimonialRepository.findAll();
    }

    public String saveTestimonial(Testimonial testimonial) {;
        testimonialRepository.save(testimonial);
        return "Testimonial Created Successfully";
    }

   public String updateTestimonial(Long id, Testimonial testimonial) {
        Optional<Testimonial> existingTestimonial = testimonialRepository.findById(id);
        
        if (existingTestimonial.isPresent()) {
            Testimonial existing = existingTestimonial.get();
            existing.setContent(testimonial.getContent());
            existing.setRating(testimonial.getRating());
            existing.setAvatar(testimonial.getAvatar());
            testimonialRepository.save(existing); // <--- Save the update
            return "Testimonial Updated Successfully";
        } else {
            throw new EntityNotFoundException("Testimonial not found");
        }
    }

    public List<TestimonialResponseDTO> getTopTestimonials() {
        List<Testimonial> topTestimonials = testimonialRepository.findTop4ByOrderByRatingDescLatestUpdateDesc();

        return topTestimonials.stream().map(testimonial -> new TestimonialResponseDTO(
                testimonial.getUser().getUserName(),
                testimonial.getUser().getRole(),
                testimonial.getContent(),
                testimonial.getRating(),
                testimonial.getAvatar(),
                testimonial.getLatestUpdate()
        )).collect(Collectors.toList());
    }
}
