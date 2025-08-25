package com.examly.springapp.model.vendor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.examly.springapp.enums.VerificationStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(
    name = "vendor_documents",
    indexes = {
        @Index(name = "idx_vendor_document_type", columnList = "documentType"),
        @Index(name = "idx_vendor_verification_status", columnList = "verificationStatus")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String documentType;

    @Column(nullable = false, unique = true)
    private String fileHash; // Integrity check for duplicate detection

    @Column(nullable = false)
    private String fileUrl; // Cloudinary URL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    private LocalDateTime uploadDate;
    private LocalDateTime verificationDate;

    @Column(length = 500)
    private String remarks; // Admin notes or rejection reason

    //=============== RELATIONSHIPS ================//

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;
}
