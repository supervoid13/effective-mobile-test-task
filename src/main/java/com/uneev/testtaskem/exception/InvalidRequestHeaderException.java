package com.uneev.testtaskem.exception;

public class InvalidRequestHeaderException extends RuntimeException {
    public InvalidRequestHeaderException(String message) {
        super(message);
    }
}
