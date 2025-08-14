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
    // 401
    UNAUTHENTICATED(4011, "Unauthenticated", HttpStatus.UNAUTHORIZED),

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
    String message;
    final HttpStatusCode httpStatusCode;

    public void addDetail(String additionalMessage) {
        this.message += " : " + additionalMessage;
    }

}
