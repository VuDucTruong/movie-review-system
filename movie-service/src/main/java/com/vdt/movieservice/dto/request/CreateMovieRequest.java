package com.vdt.movieservice.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

public record CreateMovieRequest(
    @NotEmpty(message = "MOVIE_TITLE_REQUIRED")
    String title,
    @Size(max = 400, message = "DESCRIPTION_MAX_LENGTH")
    String description,
    LocalDate releaseDate,
    String director,

    @NotNull(message = "POSTER_REQUIRED")
    MultipartFile poster,

    @NotNull(message = "TRAILER_REQUIRED")
    MultipartFile trailer,

    @NotEmpty(message = "GENRE_REQUIRED")
    Set<Long> genreIds
) {

}
