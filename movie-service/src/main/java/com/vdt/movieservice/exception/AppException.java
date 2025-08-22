package com.vdt.movieservice.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class AppException extends RuntimeException {
    ErrorCode errorCode;
    String detail;

    public AppException(ErrorCode errorCode, String detail) {
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public AppException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        detail = null;
    }
}
