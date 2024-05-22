package com.uneev.testtaskem.config;

import com.uneev.testtaskem.dto.UserParamsDto;
import com.uneev.testtaskem.entity.Email;
import com.uneev.testtaskem.entity.Phone;
import com.uneev.testtaskem.entity.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserSpecification {

    public Specification<User> build(UserParamsDto paramsDto) {
        return withDateOfBirthGt(paramsDto.getDateOfBirthGt())
                .and(withFullNameCont(paramsDto.getFullNameCont()))
                .and(withPhoneNumber(paramsDto.getPhoneNumber()))
                .and(withEmail(paramsDto.getEmail()));
    }

    private Specification<User> withDateOfBirthGt(LocalDate date) {
        return ((root, query, cb) ->
                date == null ? cb.conjunction() : cb.greaterThan(root.get("dateOfBirth"), date)
        );
    }


    private Specification<User> withPhoneNumber(String phoneNumber) {
        return ((root, query, cb) -> {
            if (phoneNumber == null)
                return cb.conjunction();

            Join<User, Phone> phones = root.join("phones");

            return cb.equal(phones.get("number"), phoneNumber);
        });
    }

    private Specification<User> withFullNameCont(String fullName) {
        return ((root, query, cb) ->
                fullName == null ? cb.conjunction() : cb.like(root.get("fullName"), fullName + "%")
        );
    }

    private Specification<User> withEmail(String email) {
        return ((root, query, cb) -> {
            if (email == null)
                return cb.conjunction();

            Join<User, Email> emails = root.join("emails");

            return cb.equal(emails.get("email"), email);
        });
    }

}
