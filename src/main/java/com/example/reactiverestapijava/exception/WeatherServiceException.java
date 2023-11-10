package com.example.reactiverestapijava.exception;

public class WeatherServiceException extends RuntimeException {
    private final int status;

    public WeatherServiceException(String message, int status) {
        super(message);
        this.status = status;
    }

    public WeatherServiceException(String message, Throwable cause, int status, String statusCheckUrl) {
        super(message, cause);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
