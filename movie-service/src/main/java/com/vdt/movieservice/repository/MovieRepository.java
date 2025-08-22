package com.vdt.movieservice.repository;

import com.vdt.movieservice.entity.Movie;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
  Page<Movie> findByGenres_IdIn(Set<Long> genreIds, Pageable pageable);
}
