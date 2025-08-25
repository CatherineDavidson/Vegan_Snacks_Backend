package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.UserProfileDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','PRODUCT_MANAGER','VENDOR')")
    @GetMapping("/me")
    public ResponseEntity<User> getMyProfile(Authentication auth) {
        User user = userService.getUserByEmail(auth.getName());
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','PRODUCT_MANAGER','VENDOR')")
    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> updateMyProfile(@RequestBody UserProfileDTO dto, Authentication auth) {
        User updatedUser = userService.updateUserProfile(auth.getName(), dto);
        return ResponseEntity.ok(updatedUser);
    }


    @PreAuthorize("hasAnyRole('CUSTOMER','PRODUCT_MANAGER','VENDOR')")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(){
        return ResponseEntity.ok(userService.deleteUser());
    }
}
