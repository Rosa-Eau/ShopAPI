package com.sparta.shopapi.global.handler.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.shopapi.global.handler.exception.ErrorCode;
import com.sparta.shopapi.global.handler.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        sendErrorResponse(response, "권한이 없습니다");
    }

    /**
     * jwt 예외처리 응답
     * @param message 예외 메세지
     */
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(
                        ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED,
                        message,
                                ErrorCode.HANDLE_ACCESS_DENIED.getStatus())));
    }
}