package com.vdt.reviewservice.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@Getter
public enum ErrorCode {

  // 400
  VALIDATION_ERROR(4000, "Validation error", HttpStatus.BAD_REQUEST),
  MOVIE_REQUIRED(4001, "Movie is required", HttpStatus.BAD_REQUEST),
  RATING_OUT_OF_BOUNDS(4002, "Rating must be between 1 and 5", HttpStatus.BAD_REQUEST),
  REVIEW_REQUIRED(4003, "Review is required", HttpStatus.BAD_REQUEST),
  USER_REQUIRED(4004, "User is required", HttpStatus.BAD_REQUEST),

  // 401
  UNAUTHENTICATED(4010, "Unauthenticated", HttpStatus.UNAUTHORIZED),
  UNAUTHORIZED(4011, "Unauthorized", HttpStatus.UNAUTHORIZED),

  // 403
  INVALID_PERMISSION(4031, "You don't have permissions to do this", HttpStatus.FORBIDDEN),

  // 404
  REVIEW_NOT_FOUND(4041, "Review not found", HttpStatus.NOT_FOUND),

  // 500
  INTERNAL_SERVER_ERROR(5000, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
  INVALID_ENUM_KEY(5001, "Invalid enum key", HttpStatus.INTERNAL_SERVER_ERROR),
  EXTRACT_TOKEN_FAILED(5002, "Extract token failed", HttpStatus.INTERNAL_SERVER_ERROR)
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
