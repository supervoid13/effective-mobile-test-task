package com.uneev.testtaskem.service;

import com.uneev.testtaskem.dto.TransferDto;
import com.uneev.testtaskem.entity.BankAccount;
import com.uneev.testtaskem.entity.User;
import com.uneev.testtaskem.exception.InvalidAmountException;
import com.uneev.testtaskem.exception.InvalidDetailsException;
import com.uneev.testtaskem.exception.NotEnoughMoneyException;
import com.uneev.testtaskem.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BankAccountService bankAccountService;

    private User userFrom;
    private BankAccount bankAccountTo;

    @BeforeEach
    public void setup() {
        User from = new User();

        from.setId(1L);
        from.setUsername("from_test_user");
        from.setBankAccount(new BankAccount(new BigDecimal("100.50")));

        userFrom = from;

        BankAccount to = new BankAccount(new BigDecimal("200.50"));
        to.setDetails("2222222222222222");
        bankAccountTo = to;

        Mockito.when(userRepository.findByUsername(userFrom.getUsername()))
                .thenReturn(Optional.of(userFrom));

        Mockito.when(bankAccountService.getByDetails(bankAccountTo.getDetails()))
                .thenReturn(Optional.of(bankAccountTo));
    }


    @Test
    public void shouldWithdrawMoneyFromAccountAndTransferToAnother() {
        TransferDto transferDto = new TransferDto(
                bankAccountTo.getDetails(),
                new BigDecimal("50.50")
        );

        userService.transfer("from_test_user", transferDto);

        BigDecimal balanceFrom = userFrom.getBankAccount().getAmount();
        BigDecimal balanceTo = bankAccountTo.getAmount();

        assertThat(balanceFrom, Matchers.comparesEqualTo(new BigDecimal("50")));
        assertThat(balanceTo, Matchers.comparesEqualTo(new BigDecimal("251")));
    }

    @Test
    public void shouldThrowInvalidAmountException() {
        TransferDto transferDto = new TransferDto(
                bankAccountTo.getDetails(),
                new BigDecimal("-10")
        );
        InvalidAmountException thrown = Assertions.assertThrows(
                InvalidAmountException.class,
                () -> userService.transfer("from_test_user", transferDto)
        );

        Assertions.assertEquals("Amount should be greater than 0", thrown.getMessage());
    }

    @Test
    public void shouldThrowInvalidDetailsException() {
        TransferDto transferDto = new TransferDto(
                "1000000000000000",
                new BigDecimal("1")
        );
        InvalidDetailsException thrown = Assertions.assertThrows(
                InvalidDetailsException.class,
                () -> userService.transfer("from_test_user", transferDto)
        );

        Assertions.assertEquals("No such bank account details", thrown.getMessage());
    }

    @Test
    public void shouldThrowNotEnoughMoneyException() {
        TransferDto transferDto = new TransferDto(
                bankAccountTo.getDetails(),
                new BigDecimal("500")
        );
        NotEnoughMoneyException thrown = Assertions.assertThrows(
                NotEnoughMoneyException.class,
                () -> userService.transfer("from_test_user", transferDto)
        );

        Assertions.assertEquals("Not enough money on your account", thrown.getMessage());
    }
}
