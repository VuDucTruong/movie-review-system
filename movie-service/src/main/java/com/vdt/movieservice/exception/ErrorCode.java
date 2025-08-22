package com.vdt.movieservice.exception;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {

    // 400
    GENRE_NAME_NOT_EMPTY(4001, "Genre name must not be empty", HttpStatus.BAD_REQUEST),
    // 401
    UNAUTHENTICATED(4011, "Unauthenticated", HttpStatus.UNAUTHORIZED),

    // 404
    GENRE_NOT_FOUND(4041, "Genre not found", HttpStatus.NOT_FOUND),
    MOVIE_NOT_FOUND(4042, "Movie not found", HttpStatus.NOT_FOUND),


    // 500
    UNKNOWN_ERROR(5002, "Unknown error" , HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_ENUM_KEY(5004, "Invalid enum key" , HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    int code;
    String message;
    HttpStatusCode httpStatusCode;

}
