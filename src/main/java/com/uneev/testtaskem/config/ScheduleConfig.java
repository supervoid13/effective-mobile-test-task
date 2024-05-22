package com.uneev.testtaskem.config;

import com.uneev.testtaskem.entity.BankAccount;
import com.uneev.testtaskem.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleConfig {

    private final BankAccountService bankAccountService;

    @Scheduled(fixedRate = 60_000)
    @Transactional
    public void scheduleAmountIncreasing() {
        List<BankAccount> accounts = bankAccountService.getAll();

        for (BankAccount account: accounts) {
            BigDecimal balance = account.getAmount();
            BigDecimal afterIncreasing = balance.add(balance.multiply(new BigDecimal("0.05")));

            if (afterIncreasing.compareTo(account.getLimit()) < 1) {
                account.setAmount(afterIncreasing);
                bankAccountService.save(account);
            }
        }
    }
}
