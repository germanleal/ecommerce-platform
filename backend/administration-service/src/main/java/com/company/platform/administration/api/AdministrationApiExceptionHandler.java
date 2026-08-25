package com.company.platform.administration.api;

import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdministrationApiExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> notFound(NoSuchElementException exception) {
        return Map.of("error", "NOT_FOUND", "message", exception.getMessage());
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String,String> forbidden(SecurityException exception) {
        return Map.of("error", "FORBIDDEN", "message", exception.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> invalid(RuntimeException exception) {
        return Map.of("error", "INVALID_REQUEST", "message", exception.getMessage());
    }
}
