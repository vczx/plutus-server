package com.vince.plutus.exception;

public class FailedToCreateUserException extends RuntimeException {
    public FailedToCreateUserException(String message) {
        super(message);
    }
}
