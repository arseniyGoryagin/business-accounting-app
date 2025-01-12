package com.meridian.exceptions;

public class PasswordOrEmailIncorrectException extends RuntimeException {
    public PasswordOrEmailIncorrectException(String message) {
        super(message);
    }
}
