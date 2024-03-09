package com.sparta.shopapi.global.handler.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // Member
    ALREADY_EXIST_EMAIL(400, "U001", "이미 가입된 이메일입니다."),
    ACCESS_DENIED_ADMIN(400, "SU002", "관리자 암호가 틀려 가입할 수 없습니다."),

    // Product
    NOT_FOUND_PRODUCT(400, "S003", "상품을 찾을 수 없습니다."),

    // Cart
    NOT_FOUND_CART(400, "S004","장바구니를 찾을 수 없습니다."),
    ACCESS_DENIED_MEMBER(400,"S006", "접근할 수 없는 장바구니 입니다."),

    ;
    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }


}