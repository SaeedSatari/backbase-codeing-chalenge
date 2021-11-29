package com.backbase.exception;

import org.springframework.http.HttpStatus;

public class CustomServiceException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;
    private final String username;

    public CustomServiceException(String message, ErrorCode errorCode, HttpStatus httpStatus, String username) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.username = username;
    }

    public CustomServiceException(String message, ErrorCode errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        username = null;
    }

    public CustomServiceException(String message) {
        super(message);
        errorCode = null;
        httpStatus = null;
        username = null;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getUsername() {
        return username;
    }
}
