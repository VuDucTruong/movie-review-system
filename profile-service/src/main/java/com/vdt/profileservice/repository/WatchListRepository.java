package com.vdt.profileservice.repository;

import com.vdt.profileservice.entity.Profile;
import com.vdt.profileservice.entity.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {
    void deleteByProfileAndMovieId(Profile profile, Long movieId);
    boolean existsByProfileAndMovieId(Profile profile, Long movieId);
    int deleteByProfileAndMovieIdIn(Profile profile, Long[] movieIds);
}
