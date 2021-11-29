package com.backbase.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ErrorMsg implements Serializable {
    private int errorCode;
    private String errorMessage;

    public ErrorMsg(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
