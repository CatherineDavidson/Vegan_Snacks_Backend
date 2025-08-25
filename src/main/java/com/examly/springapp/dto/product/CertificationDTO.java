package com.examly.springapp.dto.product;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CertificationDTO {
    private String certificationName;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String issuingAuthority;
}
