package com.meridian.clients.exceptions;

public class NoSuchClientException extends RuntimeException {
    public NoSuchClientException(String message) {
        super(message);
    }
}
