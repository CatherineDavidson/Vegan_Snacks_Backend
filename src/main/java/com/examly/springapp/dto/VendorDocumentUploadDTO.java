package com.examly.springapp.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import com.examly.springapp.enums.VerificationStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDocumentUploadDTO {

    private String documentType; // LICENSE, CERTIFICATE, INSURANCE, etc.

    private MultipartFile file;

    private VerificationStatus verificationStatus = VerificationStatus.PENDING;
}
