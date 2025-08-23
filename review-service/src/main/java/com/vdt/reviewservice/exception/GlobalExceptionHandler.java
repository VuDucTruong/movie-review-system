package com.vdt.reviewservice.exception;

import com.vdt.reviewservice.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
    log.error(e.getMessage(), e);
    ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

    var response = ApiResponse.builder().code(errorCode.getCode()).message(
        errorCode.getMessage()).build();
    return new ResponseEntity<>(response, errorCode.getHttpStatusCode());
  }

  @ExceptionHandler(AppException.class)
  ResponseEntity<ApiResponse<Object>> handleAppException(AppException e) {
    ErrorCode errorCode = e.getErrorCode();

    var response = ApiResponse.builder().code(errorCode.getCode()).message(
        errorCode.getMessage()).build();
    return new ResponseEntity<>(response, errorCode.getHttpStatusCode());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);
    ErrorCode errorCode;
    String enumKey = Objects.requireNonNull(e.getBindingResult().getFieldError())
        .getDefaultMessage();

    Map<String, Object> attributes = null;
    try {
      errorCode = ErrorCode.valueOf(enumKey);

      ConstraintViolation<?> constraintViolation = e.getBindingResult().getAllErrors().getFirst()
          .unwrap(ConstraintViolation.class);

      attributes = constraintViolation
          .getConstraintDescriptor()
          .getAttributes();

      log.error("Validation error: {}, attributes: {}", enumKey, attributes);

    } catch (IllegalArgumentException ex) {
      errorCode = ErrorCode.INVALID_ENUM_KEY;
    }

    var response = ApiResponse.builder()
        .code(errorCode.getCode())
        .message(errorCode.getMessage())
        .build();

    return new ResponseEntity<>(response, errorCode.getHttpStatusCode());

  }
}
