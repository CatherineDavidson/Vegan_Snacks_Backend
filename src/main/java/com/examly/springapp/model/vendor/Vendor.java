package com.examly.springapp.model.vendor;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.examly.springapp.enums.ComplianceStatus;
import com.examly.springapp.enums.RegistrationStatus;
import com.examly.springapp.model.Snack;
import com.examly.springapp.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    @Column(nullable = false, length = 255)
    private String companyName;

    @Column(unique = true, nullable = false, length = 255)
    private String businessEmail;

    @Column(nullable = false, length = 20)
    private String primaryContactNumber;

    // Business Information
    @Column(nullable = false, length = 500)
    private String businessDescription;

    @Column(unique = true, length = 100)
    private String businessRegistrationNumber;

    @Column(unique = true, length = 100)
    private String businessLicenseNumber; 

    @Column(length = 100)
    private String taxId;

    // @Column(length = 100)
    // private String vatNumber; // For international vendors

    @Column(nullable = false)
    private Integer establishedYear;

    @Column(length = 255)
    private String websiteUrl;

    // Approval and Compliance
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus registrationStatus;

    private LocalDate approvalDate;

    @Column(length = 100)
    private String approvedBy;

    private Boolean haccpCertified;

    private LocalDate haccpExpiryDate;

    @Column(length = 100)
    private String fdaRegistrationNumber;
 
    private LocalDate fdaRegistrationExpiry;

    @Enumerated(EnumType.STRING)
    private ComplianceStatus localComplianceStatus; // Changed to enum

    // @Column(nullable = false)
    private Boolean isVeganCertified;

    @Column(length = 100)
    private String veganCertificationBody;

    private LocalDate veganCertificationExpiry;

    // Insurance Information
    // @Column(nullable = false)
    private Boolean hasLiabilityInsurance;

    private LocalDate insuranceExpiryDate;

    private Boolean hasBusinessLicense;

    private LocalDate BusinessLicenseExpiryDate;

    // Timestamps
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    //BusineddAddress
    
    @Column(nullable = false, length = 255)
    private String streetAddress1;

    @Column(length = 255)
    private String streetAddress2;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(nullable = false, length = 20)
    private String postalCode;

    // Relationships

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Snack> snacks;

    @JsonManagedReference
    @OneToOne(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private VendorPayment vendorPayment;

    @JsonManagedReference
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VendorDocument> documents;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VendorAuditLog> auditLogs;
    
    @JsonManagedReference("vendor-compliances")
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VendorCompliance> complianceRecords;
}
