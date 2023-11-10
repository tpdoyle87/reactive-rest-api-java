package com.example.reactiverestapijava.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WeatherServiceException.class)
    public ResponseEntity<Object> handleWeatherServiceException(WeatherServiceException ex) {
        // You might want to log the exception details here
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getMessage());
        body.put("status", ex.getStatus());
        return new ResponseEntity<>(body, HttpStatusCode.valueOf(ex.getStatus()));

    }

    public static class ErrorResponse {
        private final String message;
        private final int status;

        public ErrorResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }

        // Getters
        public String getMessage() {
            return message;
        }

        public int getStatus() {
            return status;
        }
    }
}
