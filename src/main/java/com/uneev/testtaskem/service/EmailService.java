package com.uneev.testtaskem.service;

import com.uneev.testtaskem.dto.EmailChangingDto;
import com.uneev.testtaskem.dto.EmailDto;
import com.uneev.testtaskem.entity.Email;
import com.uneev.testtaskem.entity.User;
import com.uneev.testtaskem.exception.InvalidEmailException;
import com.uneev.testtaskem.repository.EmailRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class EmailService {

    private final EmailRepository emailRepository;

    public Optional<Email> getByEmail(String email) {
        return emailRepository.findByEmail(email);
    }

    public List<Email> getAllByUserId(Long userId) {
        return emailRepository.findAllByUserId(userId);
    }

    public void save(Email email) {
        emailRepository.save(email);
    }

    public void delete(Email email) {
        emailRepository.delete(email);
    }

    public void addEmail(User user, @Valid EmailDto emailDto) {
        Email email = new Email(requireAvailable(emailDto.getEmail()), user);
        save(email);
    }

    public void changeEmail(User user, @Valid EmailChangingDto emailDto) {
        List<Email> emails = getAllByUserId(user.getId());

        Optional<Email> emailToChange = emails.stream()
                .filter(x -> x.getEmail().equals(emailDto.getOldEmail()))
                .findFirst();

        if (emailToChange.isEmpty()) {
            throw new InvalidEmailException(
                    String.format("Email '%s' doesn't exists", emailDto.getOldEmail())
            );
        }

        Email email = emailToChange.get();
        email.setEmail(requireAvailable(emailDto.getNewEmail()));

        save(email);
    }

    public void deleteEmail(User user, @Valid EmailDto emailDto) {
        List<Email> emails = getAllByUserId(user.getId());

        Optional<Email> emailToDelete = emails.stream()
                .filter(x -> x.getEmail().equals(emailDto.getEmail()))
                .findFirst();

        if (emailToDelete.isEmpty()) {
            throw new InvalidEmailException(
                    String.format("Email '%s' doesn't exists", emailDto.getEmail())
            );
        }

        if (emails.size() < 2) {
            throw new InvalidEmailException(
                    String.format("Email '%s' is your last email. You can't delete it", emailDto.getEmail())
            );
        }

        delete(emailToDelete.get());
    }

    private String requireAvailable(String email) {
        Optional<Email> emailToCheck = emailRepository.findByEmail(email);

        if (emailToCheck.isPresent()) {
            throw new InvalidEmailException(
                    String.format("Email '%s' is already taken", email)
            );
        }

        return email;
    }
}
