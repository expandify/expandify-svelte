package dev.kenowi.exportify.domain.exceptions;

public class NoCredentialsException extends RuntimeException {

    public NoCredentialsException(String message) {
        super(message);
    }
}
