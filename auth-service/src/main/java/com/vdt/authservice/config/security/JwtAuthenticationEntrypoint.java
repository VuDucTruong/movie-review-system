package com.vdt.authservice.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdt.authservice.dto.ApiResponse;
import com.vdt.authservice.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntrypoint implements AuthenticationEntryPoint {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        response.setStatus(errorCode.getHttpStatusCode().value());
        response.setContentType("application/json");

        var apiResponse = ApiResponse.builder().code(errorCode.getCode()).message(
                errorCode.getMessage()).build();

        mapper.writeValue(response.getWriter(), apiResponse);

        response.flushBuffer();
    }
}
