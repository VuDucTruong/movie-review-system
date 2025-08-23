package com.vdt.reviewservice.cofig.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdt.reviewservice.dto.ApiResponse;
import com.vdt.reviewservice.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtAuthenticationEndpoint implements AuthenticationEntryPoint {

  ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
    log.error(authException.getMessage(), authException);

    response.setStatus(errorCode.getHttpStatusCode().value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    var apiResponse = ApiResponse.builder().code(errorCode.getCode()).message(
        errorCode.getMessage()).build();

    objectMapper.writeValue(response.getWriter(), apiResponse);
    response.flushBuffer();
  }
}
