package com.uneev.testtaskem.config;

import com.uneev.testtaskem.dto.ResponseInfoDto;
import com.uneev.testtaskem.dto.ValidationErrorResponseDto;
import com.uneev.testtaskem.dto.Violation;
import com.uneev.testtaskem.exception.InvalidAmountException;
import com.uneev.testtaskem.exception.InvalidDetailsException;
import com.uneev.testtaskem.exception.TransferException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ValidationGlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponseDto handleConstraintViolationException(
            ConstraintViolationException exception
    ) {
        return new ValidationErrorResponseDto(
                exception.getConstraintViolations().stream()
                        .map(
                                violation -> new Violation(
                                        violation.getPropertyPath().toString(),
                                        violation.getMessage()
                                )
                        )
                        .toList()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponseDto handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        return new ValidationErrorResponseDto(
                exception.getBindingResult().getFieldErrors().stream()
                        .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                        .toList()
        );
    }

    @ExceptionHandler(TransferException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseInfoDto handleTransferException(
            TransferException exception
    ) {
        return new ResponseInfoDto(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );
    }
}
