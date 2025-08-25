package com.examly.springapp.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class VendorComplianceDTO {
    private String complianceType;
    private String certificationNumber;
    private String issuingAuthority;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String notes;
}
