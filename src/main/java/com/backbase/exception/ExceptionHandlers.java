package com.backbase.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class ExceptionHandlers {

    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<?> backbaseServiceException(CustomServiceException exception) {
        log.error(exception.getMessage() + ",username: %s", exception.getUsername());
        return new ResponseEntity<>(new ErrorMsg(exception.getErrorCode().getValue(), exception.getMessage()), exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> backbaseServiceException(Exception exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ErrorMsg(2000, exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
