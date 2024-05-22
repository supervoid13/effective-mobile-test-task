package com.uneev.testtaskem.exception;


public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidEmailException(String msg) {
        super(msg);
    }
}
