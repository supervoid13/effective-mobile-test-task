package com.uneev.testtaskem.service;

import com.uneev.testtaskem.entity.BankAccount;
import com.uneev.testtaskem.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;


    public List<BankAccount> getAll() {
        return bankAccountRepository.findAll();
    }

    public Optional<BankAccount> getByDetails(String details) {
        return bankAccountRepository.findByDetails(details);
    }

    public void save(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }
}
