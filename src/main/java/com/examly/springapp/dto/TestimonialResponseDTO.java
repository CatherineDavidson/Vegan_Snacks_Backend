package com.examly.springapp.dto;

import java.sql.Timestamp;

import com.examly.springapp.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialResponseDTO {
    private String name;
    private Role role;
    private String content;
    private int rating;
    private String avatar;
    private Timestamp latestUpdate;
}
