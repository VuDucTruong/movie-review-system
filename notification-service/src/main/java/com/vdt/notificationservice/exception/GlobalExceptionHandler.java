package com.vdt.notificationservice.exception;

import com.vdt.notificationservice.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
    log.error(ex.getMessage());
    ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

    var response = ApiResponse.builder()
        .code(errorCode.getCode())
        .message(errorCode.getMessage())
        .build();

    return new ResponseEntity<>(response, errorCode.getHttpStatusCode());
  }

  @ExceptionHandler(AppException.class)
  ResponseEntity<Object> handleAppException(AppException ex) {
    log.error(ex.getMessage());

    var response = ApiResponse.builder()
        .code(ex.getErrorCode().getCode())
        .message(ex.getMessage() + (ex.getDetail() != null ? ": " + ex.getDetail() : ""))
        .build();

    return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatusCode());
  }

}
