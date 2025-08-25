package com.examly.springapp.service;

import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.ContactMessage;
import com.examly.springapp.repository.ContactMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public String saveMessage(ContactMessage message) {
        contactMessageRepository.save(message);
        return "Message sent successfully!";
    }

    public Page<ContactMessage> getMessages(int page, Boolean readFilter) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());

        if (readFilter != null) {
            return contactMessageRepository.findByMarkAsRead(readFilter, pageable);
        } else {
            return contactMessageRepository.findAll(pageable);
        }
    }

    public String markAsRead(Long id) {
        ContactMessage msg = contactMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        msg.setMarkAsRead(true);
        contactMessageRepository.save(msg);
        return "Message marked as read";
    }
}
