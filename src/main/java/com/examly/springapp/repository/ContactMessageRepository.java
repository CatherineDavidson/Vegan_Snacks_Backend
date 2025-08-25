package com.examly.springapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    Page<ContactMessage> findByMarkAsRead(boolean markAsRead, Pageable pageable);

    Page<ContactMessage> findAll(Pageable pageable);
}
