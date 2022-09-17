package com.sample.user.exception.api_error;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ErrorResponse {

	private HttpStatus status;
    private String message;
    private List<String> errors;
    
    public ErrorResponse(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}
