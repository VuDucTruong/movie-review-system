package com.vdt.movieservice.service;

import com.vdt.movieservice.dto.request.GenreRequest;
import com.vdt.movieservice.dto.response.GenreResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreService {
  GenreResponse createGenre(GenreRequest request);
  GenreResponse updateGenre(Long id, GenreRequest request);
  GenreResponse getGenreById(Long id);
  void deleteGenre(Long id);
  Page<GenreResponse> getAllGenres(Pageable pageable);
}
