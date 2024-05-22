package com.uneev.testtaskem.controller;

import com.uneev.testtaskem.dto.*;
import com.uneev.testtaskem.entity.User;
import com.uneev.testtaskem.service.PhoneService;
import com.uneev.testtaskem.service.UserService;
import com.uneev.testtaskem.utils.JwtTokenUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/phones")
@RequiredArgsConstructor
public class PhoneController {

    private final UserService userService;
    private final PhoneService phoneService;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("")
    public ResponseEntity<?> addPhone(
            @RequestBody PhoneDto phoneDto,
            @RequestHeader("Authorization") String jwt
    ) {
        String username = jwtTokenUtils.checkAuthorizationHeader(jwt);

        User user = userService.checkUserExists(username);

        phoneService.addPhone(user, phoneDto);

        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.CREATED.value(), "Phone successfully added"),
                HttpStatus.CREATED
        );
    }

    @PutMapping("")
    public ResponseEntity<?> changePhone(
            @RequestBody PhoneChangingDto phoneDto,
            @RequestHeader("Authorization") String jwt
    ) {
        String username = jwtTokenUtils.checkAuthorizationHeader(jwt);
        User user = userService.checkUserExists(username);

        phoneService.changePhone(user, phoneDto);

        return ResponseEntity.ok(
                new ResponseInfoDto(HttpStatus.OK.value(), "Phone successfully changed")
        );
    }

    @DeleteMapping("")
    public ResponseEntity<?> deletePhone(
            @RequestBody PhoneDto phoneDto,
            @RequestHeader("Authorization") String jwt
    ) {
        String username = jwtTokenUtils.checkAuthorizationHeader(jwt);
        User user = userService.checkUserExists(username);

        phoneService.deletePhone(user, phoneDto);

        return ResponseEntity.ok(
                new ResponseInfoDto(HttpStatus.OK.value(), "Phone successfully deleted")
        );
    }
}
