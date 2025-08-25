package com.examly.springapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.ContactMessage;
import com.examly.springapp.service.ContactMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody ContactMessage message) {
        return ResponseEntity.ok(contactMessageService.saveMessage(message));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ContactMessage>> getAllMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Boolean read // true, false or null
    ) {
        return ResponseEntity.ok(contactMessageService.getMessages(page, read));
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<String> markMessageAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(contactMessageService.markAsRead(id));
    }
}
