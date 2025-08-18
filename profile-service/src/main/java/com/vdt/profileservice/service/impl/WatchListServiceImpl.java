package com.vdt.profileservice.service.impl;

import com.vdt.profileservice.entity.Profile;
import com.vdt.profileservice.entity.WatchList;
import com.vdt.profileservice.exception.AppException;
import com.vdt.profileservice.exception.ErrorCode;
import com.vdt.profileservice.repository.ProfileRepository;
import com.vdt.profileservice.repository.WatchListRepository;
import com.vdt.profileservice.service.WatchListService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WatchListServiceImpl implements WatchListService {
    WatchListRepository watchListRepository;
    ProfileRepository profileRepository;

    @Override
    public void addToWatchList(Long movieId, Long userId) {
        Profile profile = getUserProfile(userId);
        WatchList watchList = new WatchList();
        watchList.setMovieId(movieId);
        watchList.setProfile(profile);
        watchListRepository.save(watchList);
    }

    @Override
    public void removeFromWatchList(Long movieId, Long userId) {
        Profile profile = getUserProfile(userId);
        watchListRepository.deleteByProfileAndMovieId(profile, movieId);
    }

    @Override
    public boolean isMovieInWatchList(Long movieId, Long userId) {
        Profile profile = getUserProfile(userId);
        return watchListRepository.existsByProfileAndMovieId(profile, movieId);
    }

    @Override
    public void addManyToWatchList(Long userId, Long[] movieIds) {
        Profile profile = getUserProfile(userId);
        for (Long movieId : movieIds) {
            WatchList watchList = new WatchList();
            watchList.setMovieId(movieId);
            watchList.setProfile(profile);
            watchListRepository.save(watchList);
        }
    }

    @Override
    public int removeManyFromWatchList(Long userId, Long[] movieIds) {
        Profile profile = getUserProfile(userId);
        return watchListRepository.deleteByProfileAndMovieIdIn(profile, movieIds);
    }


    private Profile getUserProfile(Long userId) {
        return profileRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}
