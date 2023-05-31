package com.spring.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> exceptionHandler(GlobalException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(ex.getCode()).body(new ErrorResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> exceptionHandler(Exception ex) {
        ex.printStackTrace();
        //默认500.
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }
}
