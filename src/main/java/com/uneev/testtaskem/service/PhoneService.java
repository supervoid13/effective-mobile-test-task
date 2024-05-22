package com.uneev.testtaskem.service;

import com.uneev.testtaskem.dto.PhoneChangingDto;
import com.uneev.testtaskem.dto.PhoneDto;
import com.uneev.testtaskem.entity.Phone;
import com.uneev.testtaskem.entity.User;
import com.uneev.testtaskem.exception.InvalidPhoneNumberException;
import com.uneev.testtaskem.repository.PhoneRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public Optional<Phone> getByNumber(String number) {
        return phoneRepository.findByNumber(number);
    }

    public List<Phone> getAllByUserId(Long userId) {
        return phoneRepository.findAllByUserId(userId);
    }

    public void save(Phone phone) {
        phoneRepository.save(phone);
    }

    public void delete(Phone phone) {
        phoneRepository.delete(phone);
    }

    public void addPhone(User user, @Valid PhoneDto phoneDto) {
        Phone phone = new Phone(requireAvailable(phoneDto.getNumber()), user);
        save(phone);
    }

    public void changePhone(User user, @Valid PhoneChangingDto phoneDto) {
        List<Phone> phones = getAllByUserId(user.getId());

        Optional<Phone> phoneToChange = phones.stream()
                .filter(x -> x.getNumber().equals(phoneDto.getOldNumber()))
                .findFirst();

        if (phoneToChange.isEmpty()) {
            throw new InvalidPhoneNumberException(
                    String.format("Phone '%s' doesn't exists", phoneDto.getOldNumber())
            );
        }

        Phone phone = phoneToChange.get();
        phone.setNumber(requireAvailable(phoneDto.getNewNumber()));

        save(phone);
    }

    public void deletePhone(User user, @Valid PhoneDto phoneDto) {
        List<Phone> phones = getAllByUserId(user.getId());

        Optional<Phone> phoneToDelete = phones.stream()
                .filter(x -> x.getNumber().equals(phoneDto.getNumber()))
                .findFirst();

        if (phoneToDelete.isEmpty()) {
            throw new InvalidPhoneNumberException(
                    String.format("Phone number '%s' doesn't exists", phoneDto.getNumber())
            );
        }

        if (phones.size() < 2) {
            throw new InvalidPhoneNumberException(
                    String.format("Phone number '%s' is your last phone number. You can't delete it", phoneDto.getNumber())
            );
        }

        delete(phoneToDelete.get());
    }

    private String requireAvailable(String phoneNumber) {
        Optional<Phone> phoneToCheck = phoneRepository.findByNumber(phoneNumber);

        if (phoneToCheck.isPresent()) {
            throw new InvalidPhoneNumberException(
                    String.format("Phone number '%s' is already taken", phoneNumber)
            );
        }

        return phoneNumber;
    }
}
