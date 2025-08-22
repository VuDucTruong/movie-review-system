package com.vdt.movieservice.mapper;

import com.vdt.movieservice.dto.request.CreateMovieRequest;
import com.vdt.movieservice.dto.request.UpdateMovieRequest;
import com.vdt.movieservice.dto.response.MovieResponse;
import com.vdt.movieservice.entity.Movie;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {GenreMapper.class})
public interface MovieMapper {

  MovieResponse toMovieResponse(Movie movie);

  @Mapping(target = "trailerUrl", ignore = true)
  @Mapping(target = "posterUrl", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "genres", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "averageRating", ignore = true)
  Movie toMovie(CreateMovieRequest createMovieRequest);

  @Mapping(target = "trailerUrl", ignore = true)
  @Mapping(target = "posterUrl", ignore = true)
  @Mapping(target = "modifiedAt", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "genres", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "averageRating", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateMovie(UpdateMovieRequest updateMovieRequest, @MappingTarget Movie movie);
}
