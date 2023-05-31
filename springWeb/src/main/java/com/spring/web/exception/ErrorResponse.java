package com.spring.web.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
    private int code;
    private String msg;

    public ErrorResponse(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }
}
