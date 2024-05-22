package com.uneev.testtaskem.exception;

import org.springframework.security.core.AuthenticationException;

public class UsernameAlreadyExistException extends AuthenticationException {

    public UsernameAlreadyExistException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UsernameAlreadyExistException(String msg) {
        super(msg);
    }
}
