package com.uneev.testtaskem.controller;

import com.uneev.testtaskem.dto.EmailChangingDto;
import com.uneev.testtaskem.dto.EmailDto;
import com.uneev.testtaskem.dto.ResponseInfoDto;
import com.uneev.testtaskem.entity.User;
import com.uneev.testtaskem.service.EmailService;
import com.uneev.testtaskem.service.UserService;
import com.uneev.testtaskem.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;


    @PostMapping("")
    public ResponseEntity<?> addEmail(
            @RequestBody EmailDto emailDto,
            @RequestHeader("Authorization") String jwt
    ) {
        String username = jwtTokenUtils.checkAuthorizationHeader(jwt);

        User user = userService.checkUserExists(username);

        emailService.addEmail(user, emailDto);

        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.CREATED.value(), "Email successfully added"),
                HttpStatus.CREATED
        );
    }

    @PutMapping("")
    public ResponseEntity<?> changeEmail(
            @RequestBody EmailChangingDto emailDto,
            @RequestHeader("Authorization") String jwt
    ) {
        String username = jwtTokenUtils.checkAuthorizationHeader(jwt);
        User user = userService.checkUserExists(username);

        emailService.changeEmail(user, emailDto);

        return ResponseEntity.ok(
                new ResponseInfoDto(HttpStatus.OK.value(), "Email successfully changed")
        );
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteEmail(
            @RequestBody EmailDto emailDto,
            @RequestHeader("Authorization") String jwt
    ) {
        String username = jwtTokenUtils.checkAuthorizationHeader(jwt);
        User user = userService.checkUserExists(username);

        emailService.deleteEmail(user, emailDto);

        return ResponseEntity.ok(
                new ResponseInfoDto(HttpStatus.OK.value(), "Email successfully deleted")
        );
    }
}
