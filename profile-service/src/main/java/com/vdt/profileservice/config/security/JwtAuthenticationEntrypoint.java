package com.vdt.profileservice.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdt.profileservice.dto.ApiResponse;
import com.vdt.profileservice.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
public class JwtAuthenticationEntrypoint implements AuthenticationEntryPoint {

  ObjectMapper mapper = new ObjectMapper();

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
    log.error(authException.getMessage(), authException);

    response.setStatus(errorCode.getHttpStatusCode().value());
    response.setContentType("application/json");

    var apiResponse = ApiResponse.builder().code(errorCode.getCode()).message(
        errorCode.getMessage()).build();

    mapper.writeValue(response.getWriter(), apiResponse);
    response.flushBuffer();
  }
}
