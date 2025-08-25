package com.examly.springapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VeganSnack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long snackId;
    private String snackName;
    private String snackType;
    private String quantity;

    @Min(value = 0, message = "Expiry in months should not be a negative value.")
    private int expiryInMonths;
    private double price;
}
