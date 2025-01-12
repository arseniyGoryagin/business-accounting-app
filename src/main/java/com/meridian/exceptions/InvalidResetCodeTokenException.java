package com.meridian.exceptions;

public class InvalidResetCodeTokenException extends RuntimeException {
    public InvalidResetCodeTokenException(String message) {
        super(message);
    }
}
