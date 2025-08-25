package com.examly.springapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDTO {
    private String userName;
    private String phone;
    private String dateOfBirth;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String country;
}
