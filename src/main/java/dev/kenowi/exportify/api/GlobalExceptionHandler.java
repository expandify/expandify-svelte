package dev.kenowi.exportify.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;


public class GlobalExceptionHandler {

    //TODO Exception handling


    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        @JsonProperty("status_code")
        private int statusCode;
        @JsonProperty("message")
        private String message;
    }
}
