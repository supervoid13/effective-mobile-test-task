package com.uneev.testtaskem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UserRegistrationDto {

    private String username;
    private String password;
    private String fullName;
    private LocalDate dateOfBirth;

    @Pattern(regexp = "[1-9]\\d{2,11}")
    private String phone;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    private String email;

    @Min(0)
    private BigDecimal initialAccountAmount;
}
