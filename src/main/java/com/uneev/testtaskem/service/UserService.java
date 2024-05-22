package com.uneev.testtaskem.service;

import com.uneev.testtaskem.dto.TransferDto;
import com.uneev.testtaskem.dto.UserRegistrationDto;
import com.uneev.testtaskem.entity.BankAccount;
import com.uneev.testtaskem.entity.Email;
import com.uneev.testtaskem.entity.Phone;
import com.uneev.testtaskem.entity.User;
import com.uneev.testtaskem.exception.InvalidAmountException;
import com.uneev.testtaskem.exception.InvalidDetailsException;
import com.uneev.testtaskem.exception.NotEnoughMoneyException;
import com.uneev.testtaskem.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PhoneService phoneService;
    private final EmailService emailService;
    private final RoleService roleService;
    private final BankAccountService bankAccountService;

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);


    public List<User> getAll(){
        return userRepository.findAll();
    }

    public List<User> getAllBySpecOnPage(int page, int size, Specification<User> specification) {
        return userRepository.findAll(specification, PageRequest.of(page - 1, size)).getContent();
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", username)
        ));

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .toList()
        );
    }

    @Transactional
    public void save(@Valid UserRegistrationDto userRegistrationDto) {
        User user = modelMapper.map(userRegistrationDto, User.class);

        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setRoles(List.of(roleService.getClientRole().orElseThrow()));

        BankAccount account = new BankAccount(userRegistrationDto.getInitialAccountAmount());

        List<BankAccount> bankAccounts = bankAccountService.getAll();
        List<String> allDetails = bankAccounts.stream()
                .map(BankAccount::getDetails)
                .toList();

        String newDetails;

        do {
            newDetails = RandomStringUtils.random(16, false, true);
        } while (allDetails.contains(newDetails) || newDetails.charAt(0) == '0');
        account.setDetails(newDetails);

        user.setBankAccount(account);

        Phone phone = new Phone(userRegistrationDto.getPhone(), user);
        Email email = new Email(userRegistrationDto.getEmail(), user);

        phoneService.save(phone);
        emailService.save(email);
    }

    @Transactional
    public void transfer(String username, @Valid TransferDto transferDto) {
        User user = checkUserExists(username);

        BigDecimal transferAmount = transferDto.getAmount();

        if (transferAmount.compareTo(BigDecimal.ZERO) < 1)
            throw new InvalidAmountException("Amount should be greater than 0");

        Optional<BankAccount> toOptional = bankAccountService.getByDetails(transferDto.getDestination());

        if (toOptional.isEmpty()) {
            throw new InvalidDetailsException("No such bank account details");
        }

        BankAccount from = user.getBankAccount();

        if (from.getAmount().compareTo(transferAmount) < 0)
            throw new NotEnoughMoneyException("Not enough money on your account");

        BankAccount to = toOptional.get();

        from.setAmount(from.getAmount().subtract(transferAmount));
        to.setAmount(to.getAmount().add(transferAmount));

        bankAccountService.save(from);
        bankAccountService.save(to);

        logger.info(
                "TRANSFER {} FROM {}({}) TO {}({})",
                transferAmount,
                from.getDetails(), from.getAmount(),
                to.getDetails(), to.getAmount()
        );
    }

    public User checkUserExists(String username) {
        return getByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User '%s' not found", username))
        );
    }
}
