package com.uneev.testtaskem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Violation {
    private String fieldName;
    private String message;
}