package com.uneev.testtaskem.exception;


public class InvalidPhoneNumberException extends RuntimeException {

    public InvalidPhoneNumberException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidPhoneNumberException(String msg) {
        super(msg);
    }
}
