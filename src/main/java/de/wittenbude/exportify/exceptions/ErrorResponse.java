package de.wittenbude.exportify.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("message")
    private String message;
}
