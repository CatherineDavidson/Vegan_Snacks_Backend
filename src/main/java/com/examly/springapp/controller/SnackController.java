package com.examly.springapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Snack;
import com.examly.springapp.service.SnackService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/snacks")
public class SnackController {

    private final SnackService snackService;

    @PostMapping
    public ResponseEntity<Snack> addSnack(@Valid @RequestBody Snack snack) {
        return ResponseEntity.ok(snackService.addSnack(snack));
    }

    @GetMapping
    public Page<Snack> getAllSnacks(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return snackService.getAllSnacks(pageable);
    }

    @GetMapping("/{name}")
    public Page<Snack> getAllSnacksByCategory(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size, @PathVariable String name) {
        Pageable pageable = PageRequest.of(page, size);
        return snackService.getAllSnacksByCategory(pageable,name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Snack> updateSnack(@PathVariable Long id, @Valid @RequestBody Snack snack) {
        return ResponseEntity.ok(snackService.updateSnack(id, snack));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSnack(@PathVariable Long id) {
        snackService.deleteSnack(id);
        return ResponseEntity.ok("Snack deleted successfully");
    }
}
