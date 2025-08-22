package com.vdt.movieservice.service;

import com.vdt.movieservice.dto.request.CreateMovieRequest;
import com.vdt.movieservice.dto.request.UpdateMovieRequest;
import com.vdt.movieservice.dto.response.MovieResponse;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {

  Page<MovieResponse> getMoviePaginationByGenreIds(Pageable pageable, Set<Long> genreIds);

  MovieResponse getMovieById(Long movieId);

  MovieResponse createMovie(CreateMovieRequest createMovieRequest);

  MovieResponse updateMovie(Long id, UpdateMovieRequest updateMovieRequest);

  void deleteMovieById(Long movieId);

}
