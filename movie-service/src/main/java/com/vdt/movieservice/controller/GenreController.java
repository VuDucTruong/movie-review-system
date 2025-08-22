package com.vdt.movieservice.controller;

import com.vdt.movieservice.dto.ApiResponse;
import com.vdt.movieservice.dto.PageResponse;
import com.vdt.movieservice.dto.request.GenreRequest;
import com.vdt.movieservice.dto.response.GenreResponse;
import com.vdt.movieservice.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genres")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GenreController {

  GenreService genreService;

  @GetMapping
  ApiResponse<PageResponse<GenreResponse>> getGenrePagination(Pageable pageable) {
    var data = PageResponse.fromPage(genreService.getAllGenres(pageable));

    return ApiResponse.<PageResponse<GenreResponse>>builder().data(data).build();
  }

  @GetMapping("/{id}")
  ApiResponse<GenreResponse> getGenreById(@PathVariable Long id) {
    return ApiResponse.<GenreResponse>builder().data(genreService.getGenreById(id)).build();
  }

  @PostMapping
  ApiResponse<GenreResponse> createGenre(@RequestBody GenreRequest genreRequest) {
    return ApiResponse.<GenreResponse>builder().data(genreService.createGenre(genreRequest))
        .build();
  }

  @PatchMapping("/{id}")
  ApiResponse<GenreResponse> updateGenre(@PathVariable Long id,
      @RequestBody GenreRequest genreRequest) {
    return ApiResponse.<GenreResponse>builder().data(genreService.updateGenre(id, genreRequest))
        .build();
  }

  @DeleteMapping("/{id}")
  ApiResponse<Void> deleteGenre(@PathVariable Long id) {
    genreService.deleteGenre(id);
    return ApiResponse.<Void>builder().message("Delete genre successfully").build();
  }


}
