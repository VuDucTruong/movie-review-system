package com.vdt.fileservice.exception;


import com.vdt.fileservice.dto.ApiResponse;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        ErrorCode errorCode = ErrorCode.UNKNOWN_ERROR;
        log.error(ex.getMessage(), ex);
        ApiResponse<Object> apiResponse = ApiResponse.builder().code(errorCode.getCode()).message(
                errorCode.getMessage()).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse<Object>> handleAppException(AppException ex) {
        var apiResponse = ApiResponse.builder()
                .code(ex.getErrorCode().getCode())
                .message(ex.getErrorCode().getMessage())
                .build();

        return new ResponseEntity<>(apiResponse, ex.getErrorCode().getHttpStatusCode());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        log.error(ex.getMessage(), ex);
        ApiResponse<Object> apiResponse = ApiResponse.builder().code(errorCode.getCode()).message(
                errorCode.getMessage() + " : " + Objects.requireNonNull(ex.getFieldError()).getDefaultMessage()).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
