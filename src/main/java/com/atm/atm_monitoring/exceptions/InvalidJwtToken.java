package com.atm.atm_monitoring.exceptions;

public class InvalidJwtToken extends Exception {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public InvalidJwtToken(String message) {
        this.message = message;
    }

}
