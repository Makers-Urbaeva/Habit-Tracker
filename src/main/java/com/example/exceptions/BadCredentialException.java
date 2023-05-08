package com.example.exceptions;

public class BadCredentialException extends RuntimeException {
    public BadCredentialException() {
    }

    public BadCredentialException(String message) {
        super(message);
    }
}
