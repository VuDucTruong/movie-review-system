package com.vdt.movieservice.repository;

import com.vdt.movieservice.entity.Genre;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

  long countByIdIn(Set<Long> ids);

  Set<Genre> findAllByIdIn(Set<Long> ids);
}
