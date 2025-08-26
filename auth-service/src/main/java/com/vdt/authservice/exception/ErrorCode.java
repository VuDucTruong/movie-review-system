package com.vdt.authservice.exception;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 400
    INVALID_EMAIL(4001, "Invalid email", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(4002, "Password must to be 6 - 40 , alphanumeric", HttpStatus.BAD_REQUEST),
    LOGIN_FAIL(4003, "Invalid email or password", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4004, "Invalid token", HttpStatus.BAD_REQUEST),
    EXPIRED_REFRESH_TOKEN(4005, "Refresh token was expired", HttpStatus.BAD_REQUEST),
    LOGOUT_FAIL(4006, "Logout fail as no access token exists", HttpStatus.BAD_REQUEST),
    INVALID_JWT_TOKEN(4007, "Invalid JWT token", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(4008, "Email already exists", HttpStatus.BAD_REQUEST),
    INVALID_OTP(4009, "Invalid OTP", HttpStatus.BAD_REQUEST),
    // 401
    UNAUTHENTICATED(4011, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    ACCOUNT_DISABLED(4012, "Your account is disabled", HttpStatus.UNAUTHORIZED),

    // 403
    INVALID_PERMISSION(4031, "You don't have permissions to do this", HttpStatus.FORBIDDEN),

    // 404
    ROLE_NOT_FOUND(4041, "Role not found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(4042, "User not found", HttpStatus.NOT_FOUND),

    // 500
    GENERATE_TOKEN_FAIL(5001, "Fail to generate token" , HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_ERROR(5002, "Unknown error" , HttpStatus.INTERNAL_SERVER_ERROR),
    EXTRACT_TOKEN_FAIL(5003, "Fail to extract token" , HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_ENUM_KEY(5004, "Invalid enum key" , HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    final int code;
    final String message;
    final HttpStatusCode httpStatusCode;

}
