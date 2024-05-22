package com.uneev.testtaskem.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmailDto {

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    private String email;
}
