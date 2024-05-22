package com.uneev.testtaskem.service;

import com.uneev.testtaskem.entity.Role;
import com.uneev.testtaskem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    Optional<Role> getClientRole() {
        return roleRepository.findByName("ROLE_CLIENT");
    }
}
