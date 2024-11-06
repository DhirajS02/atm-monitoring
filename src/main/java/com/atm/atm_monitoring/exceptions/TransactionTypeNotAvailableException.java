package com.atm.atm_monitoring.exceptions;

public class TransactionTypeNotAvailableException extends Exception{
    private final String message;

    public TransactionTypeNotAvailableException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
