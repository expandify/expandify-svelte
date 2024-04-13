package dev.kenowi.exportify.shared.exceptions;

public class InvalidRedirectUriException extends RuntimeException {
    public InvalidRedirectUriException(String message) {
        super(message);
    }
}
