package com.vdt.movieservice.exception;


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
    USER_ID_CANNOT_BE_NULL(4001, "User ID cannot be null", HttpStatus.BAD_REQUEST),
    INVALID_DISPLAY_NAME(4002, "Display name should have 3-20 characters", HttpStatus.BAD_REQUEST),
    DISPLAY_NAME_CANNOT_BE_EMPTY(4003, "Display name cannot be empty", HttpStatus.BAD_REQUEST),
    INVALID_BIO(4004, "Bio should have 200 characters at most", HttpStatus.BAD_REQUEST),
    FILE_NOT_NULL(4005, "File cannot be null", HttpStatus.BAD_REQUEST),
    // 401
    UNAUTHENTICATED(4011, "Unauthenticated", HttpStatus.UNAUTHORIZED),

    // 404
    USER_NOT_FOUND(4041, "User not found", HttpStatus.NOT_FOUND),

    // 500
    UNKNOWN_ERROR(5002, "Unknown error" , HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_ENUM_KEY(5004, "Invalid enum key" , HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    final int code;
    String message;
    final HttpStatusCode httpStatusCode;

    public void addDetail(String additionalMessage) {
        this.message += " : " + additionalMessage;
    }

}
