package com.examly.springapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.AuthRequest;
import com.examly.springapp.dto.AuthResponse;
import com.examly.springapp.dto.RegisterRequest;
import com.examly.springapp.dto.RegistrationWrapper;
import com.examly.springapp.dto.VendorRegistrationDTO;
import com.examly.springapp.service.AuthService;
import com.examly.springapp.service.DocumentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register( @Valid @RequestBody RegistrationWrapper wrapper) {
        return ResponseEntity.ok(authService.register(wrapper.getUser(), wrapper.getVendor()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    private final DocumentService documentService;

    // @PostMapping("/testUpload")
    // public String upload(@RequestParam("file") MultipartFile file) throws IOException {
    //     return documentService.uploadVendorDocument(file);
    // }
}
