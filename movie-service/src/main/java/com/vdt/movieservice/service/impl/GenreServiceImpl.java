package com.vdt.movieservice.service.impl;

import com.vdt.movieservice.dto.request.GenreRequest;
import com.vdt.movieservice.dto.response.GenreResponse;
import com.vdt.movieservice.exception.AppException;
import com.vdt.movieservice.exception.ErrorCode;
import com.vdt.movieservice.mapper.GenreMapper;
import com.vdt.movieservice.repository.GenreRepository;
import com.vdt.movieservice.service.GenreService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
  GenreRepository genreRepository;
  GenreMapper genreMapper;

  @Override
  public GenreResponse createGenre(GenreRequest request) {
    var genre = genreMapper.mapGenreRequestToGenre(request);
    var savedGenre = genreRepository.save(genre);
    return genreMapper.mapGenreToGenreResponse(savedGenre);
  }

  @Override
  public GenreResponse updateGenre(Long id, GenreRequest request) {
    var genre = genreRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.GENRE_NOT_FOUND));
    genreMapper.updateGenreFromRequest(request, genre);

    var updatedGenre = genreRepository.save(genre);
    return genreMapper.mapGenreToGenreResponse(updatedGenre);
  }

  @Override
  public GenreResponse getGenreById(Long id) {
    var genre = genreRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.GENRE_NOT_FOUND));
    return genreMapper.mapGenreToGenreResponse(genre);
  }

  @Override
  public void deleteGenre(Long id) {
    genreRepository.deleteById(id);
  }

  @Override
  public Page<GenreResponse> getAllGenres(Pageable pageable) {
    return genreRepository.findAll(pageable)
        .map(genreMapper::mapGenreToGenreResponse);
  }
}
