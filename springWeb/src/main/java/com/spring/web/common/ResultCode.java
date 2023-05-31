package com.spring.web.common;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(10000, "请求成功"),
    FAILED(10001, "操作失败"),
    TOKEN_FAILED(10002, "token失效"),

    NONE(99999, "无");

    private int code;
    private String msg;

    private ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
