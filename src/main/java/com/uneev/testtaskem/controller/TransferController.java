package com.uneev.testtaskem.controller;

import com.uneev.testtaskem.dto.ResponseInfoDto;
import com.uneev.testtaskem.dto.TransferDto;
import com.uneev.testtaskem.service.UserService;
import com.uneev.testtaskem.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final UserService userService;

    private final JwtTokenUtils jwtTokenUtils;


    @PostMapping("")
    public ResponseEntity<?> transfer(
            @RequestBody TransferDto transferDto,
            @RequestHeader("Authorization") String jwt
    ) {
        String username = jwtTokenUtils.checkAuthorizationHeader(jwt);

        userService.transfer(username, transferDto);

        return ResponseEntity.ok(
                new ResponseInfoDto(HttpStatus.OK.value(), "Successfully transferred")
        );
    }
}
