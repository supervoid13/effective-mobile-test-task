package com.uneev.testtaskem.exception;

public class InvalidAmountException extends TransferException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
