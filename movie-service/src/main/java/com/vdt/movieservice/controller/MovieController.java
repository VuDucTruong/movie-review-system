package com.vdt.movieservice.controller;

import com.vdt.movieservice.dto.ApiResponse;
import com.vdt.movieservice.dto.PageResponse;
import com.vdt.movieservice.dto.request.CreateMovieRequest;
import com.vdt.movieservice.dto.request.UpdateMovieRequest;
import com.vdt.movieservice.dto.response.MovieResponse;
import com.vdt.movieservice.service.MovieService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MovieController {
  MovieService movieService;

  @GetMapping
  ApiResponse<PageResponse<MovieResponse>> getMoviePaginationByGenreIds(@RequestParam Set<Long> genreIds, Pageable pageable) {
    var data = movieService.getMoviePaginationByGenreIds(pageable, genreIds);

    return ApiResponse.<PageResponse<MovieResponse>>builder().data(PageResponse.fromPage(data)).build();
  }

  @GetMapping("/{id}")
  ApiResponse<MovieResponse> getMovieById(@PathVariable Long id) {
    return ApiResponse.<MovieResponse>builder().data(movieService.getMovieById(id)).build();
  }

  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @PreAuthorize("hasRole('ADMIN_CREATE')")
  ApiResponse<MovieResponse> createMovie(@ModelAttribute CreateMovieRequest request){
    return ApiResponse.<MovieResponse>builder().data(movieService.createMovie(request)).build();
  }

  @PatchMapping(value = "/{id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @PreAuthorize("hasRole('ADMIN_UPDATE')")
  ApiResponse<MovieResponse> updateMovie(@PathVariable Long id,@ModelAttribute UpdateMovieRequest request){
    return ApiResponse.<MovieResponse>builder().data(movieService.updateMovie(id,request)).build();
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN_DELETE')")
  ApiResponse<MovieResponse> deleteMovie(@PathVariable Long id) {
    movieService.deleteMovieById(id);
    return ApiResponse.<MovieResponse>builder().message("Delete successfully").build();
  }
}
