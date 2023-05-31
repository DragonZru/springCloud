package com.spring.web.exception;

import org.springframework.http.HttpStatus;

public class GlobalException extends RuntimeException {

    private int code;

    public GlobalException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public GlobalException(HttpStatus status, String msg) {
        super(msg);
        this.code = status.value();
    }

    public int getCode() {
        return code;
    }
}
