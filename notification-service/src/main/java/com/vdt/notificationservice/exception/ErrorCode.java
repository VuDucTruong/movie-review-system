package com.vdt.notificationservice.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@Getter
public enum ErrorCode {

  // 401
  UNAUTHENTICATED(4010, "Unauthenticated", HttpStatus.UNAUTHORIZED),

  INTERNAL_SERVER_ERROR(5000, "Internal server error", HttpStatusCode.valueOf(500)),

  ;
  int code;
  String message;
  HttpStatusCode httpStatusCode;

  ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
    this.code = code;
    this.message = message;
    this.httpStatusCode = httpStatusCode;
  }
}
