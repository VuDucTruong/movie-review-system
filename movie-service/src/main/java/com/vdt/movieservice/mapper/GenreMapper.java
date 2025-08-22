package com.vdt.movieservice.mapper;

import com.vdt.movieservice.dto.request.GenreRequest;
import com.vdt.movieservice.dto.response.GenreResponse;
import com.vdt.movieservice.entity.Genre;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GenreMapper {

  GenreResponse mapGenreToGenreResponse(Genre genre);

  @Mapping(target = "id", ignore = true)
  Genre mapGenreRequestToGenre(GenreRequest request);


  @Mapping(target = "id", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  void updateGenreFromRequest(GenreRequest request, @MappingTarget Genre genre);
}
