package com.uneev.testtaskem.exception;

public class NotEnoughMoneyException extends TransferException{
    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
