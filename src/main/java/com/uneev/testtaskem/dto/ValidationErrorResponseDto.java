package com.uneev.testtaskem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationErrorResponseDto {
    private List<Violation> violations;
}