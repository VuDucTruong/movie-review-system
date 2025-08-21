package com.vdt.fileservice.exception;


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

    ///  401
    UNAUTHORIZED_ACCESS(4011, "Unauthorized access to remove file", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(4012, "Unauthenticated", HttpStatus.UNAUTHORIZED),


    // 400
    VALIDATION_ERROR(4001, "Validation error", HttpStatus.BAD_REQUEST),
    ///  404
    FILE_NOT_FOUND(4041, "File not found", HttpStatus.NOT_FOUND),

    // 500
    UPLOAD_FILE_ERROR(5001, "Upload file error", HttpStatus.INTERNAL_SERVER_ERROR),
    DOWNLOAD_FILE_ERROR(5002, "Download file error", HttpStatus.INTERNAL_SERVER_ERROR),
    DELETE_FILE_ERROR(5003, "Delete file error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_ERROR(5004, "Unknown error", HttpStatus.INTERNAL_SERVER_ERROR)
    ;

    final int code;
    String message;
    final HttpStatusCode httpStatusCode;

    public void addDetail(String additionalMessage) {
        this.message += " : " + additionalMessage;
    }

}
