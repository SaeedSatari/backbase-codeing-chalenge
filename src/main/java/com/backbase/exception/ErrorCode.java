package com.backbase.exception;

public enum ErrorCode {

    MOVIE_NOT_FOUND("Movie not found", 1001),
    TOP_TEN_NOT_FOUND("The top ten is not available", 1002),
    USER_EXIST("User already exist", 1003),
    USER_NOT_FOUND("User not found", 1004),
    BOX_OFFICE_NOT_FOUND("Box office not found", 1005),
    RATE_INVALID_RANGE("The rate should between 0 and 10", 1006),
    INTERNAL_ERROR("Internal error", 1007);

    private int value;
    private String message;

    ErrorCode(String message, int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }
}
