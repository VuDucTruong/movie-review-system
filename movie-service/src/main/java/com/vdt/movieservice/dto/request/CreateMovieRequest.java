package com.vdt.movieservice.dto.request;


import java.time.LocalDate;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

public record CreateMovieRequest(
    String title,
    String description,
    LocalDate releaseDate,
    String director,
    MultipartFile poster,
    MultipartFile trailer,
    Set<Long> genreIds
) {

}
