package com.examly.springapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String role;
    // private String userName;

    public AuthResponse(String token, String role){
        this.token = token;
        this.role = role;
    }
}
