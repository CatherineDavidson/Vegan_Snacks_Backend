package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.VeganSnack;
import com.examly.springapp.service.VeganSnackService;

import jakarta.validation.Valid;

@RestController
public class VeganSnackController {
    
    @Autowired
    private VeganSnackService veganSnackService;

    @PostMapping("/addVeganSnack")
    public ResponseEntity<?> addVeganSnack(@Valid @RequestBody VeganSnack snack){
        VeganSnack savedSnack = veganSnackService.createSnack(snack);
        return ResponseEntity.ok(savedSnack);
    }

    @GetMapping("/getAllVeganSnacks")
    public List<VeganSnack> getAllSnacks(){
        return veganSnackService.getAllSnacks();
    }
}
