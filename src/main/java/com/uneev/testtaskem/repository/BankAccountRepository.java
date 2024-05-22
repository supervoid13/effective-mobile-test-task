package com.uneev.testtaskem.repository;

import com.uneev.testtaskem.entity.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {
    List<BankAccount> findAll();

    Optional<BankAccount> findByDetails(String details);
}
