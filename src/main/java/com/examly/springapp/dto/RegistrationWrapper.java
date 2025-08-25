package com.examly.springapp.dto;

import lombok.Data;

@Data
public class RegistrationWrapper {
    private RegisterRequest user;
    private VendorRegistrationDTO vendor;
}
