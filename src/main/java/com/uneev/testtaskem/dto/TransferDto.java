package com.uneev.testtaskem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransferDto {

    @Pattern(regexp = "^[1-9][0-9]{15}$")
    private String destination;

    @Min(0)
    private BigDecimal amount;
}
