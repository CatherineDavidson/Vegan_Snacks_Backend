package com.examly.springapp.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponseDTO {

    private Long vendorId;
    private String companyName;
    private String businessEmail;
    private String primaryContactNumber;
    private String businessDescription;
    private String businessRegistrationNumber;
    private String taxId;
    //TODO: if needed -> private String vatNumber;
    private Integer establishedYear;
    private String websiteUrl;
    private String registrationStatus;

    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String postalCode;

    private Boolean haccpCertified;
    private LocalDate haccpExpiryDate;
    private String fdaRegistrationNumber;
    private LocalDate fdaRegistrationExpiry;
    private Boolean isVeganCertified;
    private String veganCertificationBody;
    private LocalDate veganCertificationExpiry;
    private Boolean hasLiabilityInsurance;
    private LocalDate insuranceExpiryDate;

    //TODO: if needed -> private List<String> documentUrls;
}
