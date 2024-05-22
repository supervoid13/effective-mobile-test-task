package com.uneev.testtaskem.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserParamsDto {
    private LocalDate dateOfBirthGt;
    private String phoneNumber;
    private String fullNameCont;
    private String email;
}
