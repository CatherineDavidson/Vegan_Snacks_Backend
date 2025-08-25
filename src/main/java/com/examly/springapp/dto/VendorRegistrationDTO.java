package com.examly.springapp.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorRegistrationDTO {

    // Business Information
    @NotBlank
    private String companyName;

    @Email
    @NotBlank
    private String businessEmail;

    @NotBlank
    private String primaryContactNumber;

    @NotBlank
    private String businessDescription;

    @NotBlank
    private String businessRegistrationNumber;

    private String businessLicenseNumber; // optional, file upload handled separately

    private String taxId;

    @NotNull
    private Integer establishedYear;

    private String websiteUrl;

    private String fdaRegistrationNumber;

    // Business Address
    @NotBlank
    private String streetAddress1;

    private String streetAddress2;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String postalCode;

    // Banking Details
    private String bankName;

    private String accountHolderName;

    @NotBlank
    private String bankAccountNumber; // plain, encrypt in service

    private String routingNumber;

    private String swiftCode;

    private String accountType; // CHECKING, SAVINGS, BUSINESS

    private String processorAccountId;

    private String verificationMethod;
}
