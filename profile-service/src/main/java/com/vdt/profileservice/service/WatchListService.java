package com.vdt.profileservice.service;

public interface WatchListService {
    void addToWatchList(Long movieId, Long userId);
    void removeFromWatchList(Long movieId, Long userId);
    boolean isMovieInWatchList(Long movieId, Long userId);
    void addManyToWatchList(Long userId, Long[] movieIds);
    int removeManyFromWatchList(Long userId, Long[] movieIds);
    // TODO: implement get my watch list after implement movie service
}
