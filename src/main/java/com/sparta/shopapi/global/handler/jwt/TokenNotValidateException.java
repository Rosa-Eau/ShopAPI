package com.sparta.shopapi.global.handler.jwt;

import io.jsonwebtoken.JwtException;

public class TokenNotValidateException extends JwtException {

    public TokenNotValidateException(String message) {
        super(message);
    }

    public TokenNotValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}