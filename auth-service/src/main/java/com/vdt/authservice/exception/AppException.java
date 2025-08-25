package com.vdt.authservice.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Setter
public class AppException extends RuntimeException {

  ErrorCode errorCode;
  String detail;

  public AppException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.detail = null;
  }

  public AppException(ErrorCode errorCode, String detail) {
    this.errorCode = errorCode;
    this.detail = detail;
  }
}
