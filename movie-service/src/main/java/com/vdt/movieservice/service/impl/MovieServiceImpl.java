package com.vdt.movieservice.service.impl;

import com.vdt.movieservice.dto.request.CreateMovieRequest;
import com.vdt.movieservice.dto.request.UpdateMovieRequest;
import com.vdt.movieservice.dto.response.MovieResponse;
import com.vdt.movieservice.entity.Genre;
import com.vdt.movieservice.exception.AppException;
import com.vdt.movieservice.exception.ErrorCode;
import com.vdt.movieservice.mapper.MovieMapper;
import com.vdt.movieservice.repository.GenreRepository;
import com.vdt.movieservice.repository.MovieRepository;
import com.vdt.movieservice.repository.client.FileClient;
import com.vdt.movieservice.repository.client.ReviewClient;
import com.vdt.movieservice.service.MovieService;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

  MovieRepository movieRepository;
  MovieMapper movieMapper;
  FileClient fileClient;
  GenreRepository genreRepository;
  ReviewClient reviewClient;

  @Override
  public Page<MovieResponse> getMoviePaginationByGenreIds(Pageable pageable, Set<Long> genreIds) {
    return movieRepository.findByGenres_IdIn(genreIds, pageable).map(movieMapper::toMovieResponse);
  }

  @Override
  public MovieResponse getMovieById(Long movieId) {
    var movie = movieRepository.findById(movieId)
        .orElseThrow(() -> new AppException(ErrorCode.GENRE_NOT_FOUND));
    return movieMapper.toMovieResponse(movie);
  }

  @Override
  public MovieResponse createMovie(CreateMovieRequest createMovieRequest) {

    var movie = movieMapper.toMovie(createMovieRequest);

    if (createMovieRequest.poster() != null) {
      var posterUrl = fileClient.uploadFile(createMovieRequest.poster()).getData().url();

      movie.setPosterUrl(posterUrl);
    }

    if (createMovieRequest.trailer() != null) {
      var trailerUrl = fileClient.uploadFile(createMovieRequest.trailer()).getData().url();

      movie.setTrailerUrl(trailerUrl);
    }

    Set<Long> requestedIds = createMovieRequest.genreIds(); // assuming request has Set<Long> genreIds
    if (requestedIds != null && !requestedIds.isEmpty()) {
      Set<Genre> genres = new HashSet<>(genreRepository.findAllById(requestedIds));

      if (genres.size() != requestedIds.size()) {
        // Find which IDs are missing
        Set<Long> foundIds = genres.stream()
            .map(Genre::getId)
            .collect(Collectors.toSet());

        Set<Long> missingIds = new HashSet<>(requestedIds);
        missingIds.removeAll(foundIds);

        throw new AppException(ErrorCode.GENRE_NOT_FOUND,
            StringUtils.arrayToCommaDelimitedString(missingIds.toArray()));
      }

      movie.setGenres(genres);
    }

    var savedMovie = movieRepository.save(movie);

    reviewClient.createReviewStatistic(savedMovie.getId());

    return movieMapper.toMovieResponse(savedMovie);

  }

  @Override
  public MovieResponse updateMovie(Long id, UpdateMovieRequest updateMovieRequest) {
    var movie = movieRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_FOUND));

    movieMapper.updateMovie(updateMovieRequest, movie);

    if (updateMovieRequest.poster() != null) {
      var posterUrl = fileClient.uploadFile(updateMovieRequest.poster()).getData().url();

      movie.setPosterUrl(posterUrl);
    }

    if (updateMovieRequest.trailer() != null) {
      var trailerUrl = fileClient.uploadFile(updateMovieRequest.trailer()).getData().url();

      movie.setTrailerUrl(trailerUrl);
    }

    Set<Long> requestedIds = updateMovieRequest.genreIds(); // assuming request has Set<Long> genreIds
    if (requestedIds != null && !requestedIds.isEmpty()) {
      Set<Genre> genres = new HashSet<>(genreRepository.findAllById(requestedIds));

      if (genres.size() != requestedIds.size()) {
        // Find which IDs are missing
        Set<Long> foundIds = genres.stream()
            .map(Genre::getId)
            .collect(Collectors.toSet());

        Set<Long> missingIds = new HashSet<>(requestedIds);
        missingIds.removeAll(foundIds);

        throw new AppException(ErrorCode.GENRE_NOT_FOUND,
            StringUtils.arrayToCommaDelimitedString(missingIds.toArray()));
      }

      movie.setGenres(genres);
    }

    return movieMapper.toMovieResponse(movieRepository.save(movie));
  }

  @Override
  public void deleteMovieById(Long movieId) {
    movieRepository.deleteById(movieId);
  }

}
