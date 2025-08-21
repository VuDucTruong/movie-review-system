package com.vdt.movieservice.exception;


import com.vdt.movieservice.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import java.util.Map;
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
    ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        String errorCodeKey = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        ErrorCode errorCode;
        Map<String , Object> attributes = null;
        log.error("Validation error: {}", errorCodeKey, ex);
        try {
            errorCode = ErrorCode.valueOf(errorCodeKey);

            ConstraintViolation<?> constraints = ex.getAllErrors().getFirst()
                    .unwrap(ConstraintViolation.class);

            attributes = constraints
                    .getConstraintDescriptor()
                    .getAttributes();


            log.error(attributes.toString());

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            errorCode = ErrorCode.INVALID_ENUM_KEY;
        }

        var apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

}
