package dev.kenowi.exportify.snapshot.api.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.kenowi.exportify.shared.exceptions.EntityNotFoundException;
import dev.kenowi.exportify.shared.exceptions.InvalidRedirectUriException;
import dev.kenowi.exportify.shared.exceptions.NoCredentialsException;
import dev.kenowi.exportify.shared.exceptions.SnapshotNotReadyException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRedirectUriException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidRedirectUriException(InvalidRedirectUriException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(NoCredentialsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleNoCredentialsException(NoCredentialsException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(SnapshotNotReadyException.class)
    public ResponseEntity<ErrorResponse> handleSnapshotNotReadyException(SnapshotNotReadyException ex) {
        return buildResponse(HttpStatus.ACCEPTED, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), message));
    }


    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        @JsonProperty("status_code")
        private int statusCode;
        @JsonProperty("message")
        private String message;
    }
}
