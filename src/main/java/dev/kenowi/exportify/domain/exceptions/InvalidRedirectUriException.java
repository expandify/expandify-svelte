package dev.kenowi.exportify.domain.exceptions;

public class InvalidRedirectUriException extends RuntimeException {
    public InvalidRedirectUriException(String message) {
        super(message);
    }
}
