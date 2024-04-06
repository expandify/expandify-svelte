package dev.kenowi.exportify.domain.exception;

public class InvalidRedirectUriException extends RuntimeException {
    public InvalidRedirectUriException(String message) {
        super(message);
    }
}
