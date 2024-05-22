package com.uneev.testtaskem.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PhoneDto {

    @Pattern(regexp = "[1-9]\\d{2,11}")
    private String number;
}
