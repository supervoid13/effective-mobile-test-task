package com.uneev.testtaskem.repository;

import com.uneev.testtaskem.entity.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends CrudRepository<Email, Long> {
    Optional<Email> findByEmail(String email);

    List<Email> findAllByUserId(Long userId);
}
