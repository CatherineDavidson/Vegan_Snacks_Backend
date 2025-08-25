package com.examly.springapp.model.vendor;

import java.time.LocalDate;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.examly.springapp.enums.ComplianceStatus;
import com.examly.springapp.enums.ComplianceType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorCompliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complianceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplianceType complianceType ;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplianceStatus status = ComplianceStatus.PENDING;

    @Column(length = 100)
    private String certificationNumber;

    @Column(length = 255)
    private String issuingAuthority;

    private LocalDate issueDate;
    private LocalDate expiryDate;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false)
    private Boolean requiresRenewal;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    // Relationship to Vendor
    @JsonBackReference("vendor-compliances")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;
}
