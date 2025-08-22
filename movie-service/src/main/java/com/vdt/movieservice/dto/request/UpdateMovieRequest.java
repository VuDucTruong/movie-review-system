package com.vdt.movieservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

public record UpdateMovieRequest(
    @NotBlank(message = "MOVIE_TITLE_REQUIRED")
    String title,
    String description,
    LocalDate releaseDate,
    String director,
    MultipartFile poster,
    MultipartFile trailer,
    Set<Long> genreIds
) {

}
