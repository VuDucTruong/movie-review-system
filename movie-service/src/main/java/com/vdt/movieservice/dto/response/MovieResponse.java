package com.vdt.movieservice.dto.response;

import java.time.LocalDate;
import java.util.Set;

public record MovieResponse(
    Long id,
    String title,
    String description,
    LocalDate releaseDate,
    String director,
    Double averageRating,
    String posterUrl,
    String trailerUrl,
    LocalDate createdAt,
    LocalDate modifiedAt,
    Set<GenreResponse> genres
) {

}
