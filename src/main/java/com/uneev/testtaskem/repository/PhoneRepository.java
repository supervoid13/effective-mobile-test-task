package com.uneev.testtaskem.repository;

import com.uneev.testtaskem.entity.Phone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneRepository extends CrudRepository<Phone, Long> {
    Optional<Phone> findByNumber(String number);

    List<Phone> findAllByUserId(Long userId);
}
