package com.vince.plutus.exception;

public class NegativeBalanceException extends Exception {
    public static final int CODE = -100;

    public NegativeBalanceException(String message) {
        super(message);
    }
}
