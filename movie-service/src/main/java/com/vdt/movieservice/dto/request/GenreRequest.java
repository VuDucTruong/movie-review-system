package com.vdt.movieservice.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record GenreRequest(
    @NotEmpty(message="GENRE_NAME_NOT_EMPTY")
    String name
) {

}
