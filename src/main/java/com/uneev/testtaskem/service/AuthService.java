package com.uneev.testtaskem.service;

import com.uneev.testtaskem.dto.UserRegistrationDto;
import com.uneev.testtaskem.exception.InvalidEmailException;
import com.uneev.testtaskem.exception.UsernameAlreadyExistException;
import com.uneev.testtaskem.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PhoneService phoneService;
    private final EmailService emailService;

    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public String createToken(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,
                password
        ));

        UserDetails userDetails = userService.loadUserByUsername(username);

        return jwtTokenUtils.generateToken(userDetails);
    }

    public void createUser(UserRegistrationDto userRegistrationDto) {
        if (userService.getByUsername(userRegistrationDto.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException(
                    String.format("Username '%s' is already taken", userRegistrationDto.getUsername())
            );
        }

        if (emailService.getByEmail(userRegistrationDto.getEmail()).isPresent()) {
            throw new InvalidEmailException(
                    String.format("Email '%s' is already taken", userRegistrationDto.getEmail())
            );
        }

        if (phoneService.getByNumber(userRegistrationDto.getPhone()).isPresent()) {
            throw new InvalidEmailException(
                    String.format("Phone number '%s' is already taken", userRegistrationDto.getPhone())
            );
        }

        userService.save(userRegistrationDto);
    }
}
