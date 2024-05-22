package com.uneev.testtaskem.config;

import com.uneev.testtaskem.dto.ResponseInfoDto;
import com.uneev.testtaskem.exception.InvalidEmailException;
import com.uneev.testtaskem.exception.InvalidPhoneNumberException;
import com.uneev.testtaskem.exception.UsernameAlreadyExistException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleBadCredentialsException(BadCredentialsException exception) {

        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.BAD_REQUEST.value(), "Invalid username or password"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleExpiredJwtException(ExpiredJwtException exception) {

        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.UNAUTHORIZED.value(), "Token has been expired"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleSignatureException(SignatureException exception) {

        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.UNAUTHORIZED.value(), "Invalid token signature"),
                HttpStatus.UNAUTHORIZED
        );
    }


    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleUsernameAlreadyExistException(
            UsernameAlreadyExistException exception
    ) {

        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.BAD_REQUEST.value(),  exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handleEmailAlreadyTakenException(
            InvalidEmailException exception
    ) {

        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.BAD_REQUEST.value(),  exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<ResponseInfoDto> handlePhoneNumberAlreadyTakenException(
            InvalidPhoneNumberException exception
    ) {

        return new ResponseEntity<>(
                new ResponseInfoDto(HttpStatus.BAD_REQUEST.value(),  exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
