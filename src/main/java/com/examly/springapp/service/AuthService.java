package com.examly.springapp.service;

import java.sql.Timestamp;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examly.springapp.dto.AuthRequest;
import com.examly.springapp.dto.AuthResponse;
import com.examly.springapp.dto.RegisterRequest;
import com.examly.springapp.dto.VendorRegistrationDTO;
import com.examly.springapp.enums.Role;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final VendorService vendorService;

    @Transactional
    public AuthResponse register(RegisterRequest request,VendorRegistrationDTO vendorDTO){

        if (request.getRole().equalsIgnoreCase("ADMIN")) {
            throw new IllegalArgumentException("Registration as ADMIN is not allowed.");
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        try {
            Role erole = Role.valueOf(request.getRole().toUpperCase());
            user.setRole(erole);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + request.getRole());
        }
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        userRepository.save(user);

        /** For extended vendor Registration */

        if (user.getRole() == Role.VENDOR) {
            if (vendorDTO == null) {
                throw new IllegalArgumentException("Vendor details are required for vendor registration.");
            }
            vendorService.registerVendor(user, vendorDTO);
        }

        //JWT to login immediately after registration
        String token = jwtService.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token,user.getRole().name());

    }

    public AuthResponse login(AuthRequest request) {

            authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        //     throw new RuntimeException("Invalid credentials");
        // }

        String token = jwtService.generateToken(user.getEmail(),user.getRole());
        return new AuthResponse(token,user.getRole().name());
    }

}
