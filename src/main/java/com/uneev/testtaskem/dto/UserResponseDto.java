package com.uneev.testtaskem.dto;

import com.uneev.testtaskem.entity.BankAccount;
import com.uneev.testtaskem.entity.Email;
import com.uneev.testtaskem.entity.Phone;
import com.uneev.testtaskem.entity.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
public class UserResponseDto {

    private Long id;
    private String username;
    private String fullName;
    private LocalDate dateOfBirth;
    private String bankAccountDetails;
    private List<PhoneDto> phones;
    private List<EmailDto> emails;
}
