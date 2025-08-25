package com.examly.springapp.model.vendor;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.examly.springapp.enums.PaymentAccountStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    // Bank Account Information (Encrypted)
    @Column(length = 100)
    private String bankName;

    @Column(length = 100)
    private String accountHolderName;

    @Column(length = 255)
    private String encryptedAccountNumber; // Encrypted

    @Column(length = 20)
    private String routingNumber;

    @Column(length = 20)
    private String swiftCode; // For international payments

    @Column(length = 10)
    private String accountType; // CHECKING, SAVINGS, BUSINESS

    // Payment Processor Information
    // @Enumerated(EnumType.STRING)
    // private PaymentProcessor preferredProcessor;

    @Column(length = 255)
    private String processorAccountId; // Stripe Connect Account ID, PayPal Merchant ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentAccountStatus accountStatus;

    // Tax Information
    @Column(length = 50)
    private String taxFormType; // W9, W8, etc.

    @Column(nullable = false)
    private Boolean taxFormSubmitted;

    private LocalDate taxFormSubmissionDate;

    // Payment Terms
    @Column(nullable = false)
    private Integer paymentTermsDays; // Net 30, Net 15, etc.

    @Column(length = 10)
    private String preferredCurrency;

    // Verification
    @Column(nullable = false)
    private Boolean isBankAccountVerified;

    private LocalDate bankAccountVerificationDate;

    @Column(length = 500)
    private String verificationMethod;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    //=============== RELATIONSHIPS ================//
    
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;
}

enum PaymentProcessor {
    STRIPE,
    PAYPAL,
    SQUARE,
    BANK_TRANSFER,
    CHECK,
    ACH
}

