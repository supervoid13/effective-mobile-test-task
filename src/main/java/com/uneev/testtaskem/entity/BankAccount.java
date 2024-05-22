package com.uneev.testtaskem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

@Entity
@Table(name = "bank_accounts")
@Data
@NoArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "details", nullable = false, unique = true)
    private String details;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "increasing_limit", nullable = false)
    private BigDecimal limit;

    public BankAccount(BigDecimal amount) {
        this.amount = amount;
        this.limit = amount.multiply(new BigDecimal("2.07"));
    }
}
