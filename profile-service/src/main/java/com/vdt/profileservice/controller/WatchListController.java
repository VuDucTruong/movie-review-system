package com.vdt.profileservice.controller;

import com.vdt.profileservice.dto.ApiResponse;
import com.vdt.profileservice.service.WatchListService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/watchlist")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WatchListController {
    WatchListService watchListService;

    @GetMapping("/check")
    ApiResponse<Boolean> isMovieInWatchList(@NotNull Long movieId,@NotNull Long userId) {
        boolean exists = watchListService.isMovieInWatchList(movieId, userId);
        return ApiResponse.<Boolean>builder()
                .message("Check if movie is in watchlist")
                .data(exists)
                .build();
    }


    @PostMapping("/add")
    ApiResponse<Void> addToWatchList(@NotNull Long movieId,@NotNull Long userId) {
        watchListService.addToWatchList(movieId, userId);
        return ApiResponse.<Void>builder().message("Added to watchlist").build();
    }

    @PostMapping("/add-many")
    ApiResponse<Void> addManyToWatchList(@NotNull Long userId,@NotEmpty Long[] movieIds) {
        watchListService.addManyToWatchList(userId, movieIds);
        return ApiResponse.<Void>builder().message("Added multiple movies to watchlist").build();
    }

    @DeleteMapping("/remove")
    ApiResponse<Void> removeFromWatchList(@NotNull Long movieId,@NotNull Long userId) {
        watchListService.removeFromWatchList(movieId, userId);
        return ApiResponse.<Void>builder().message("Removed from watchlist").build();
    }

    @DeleteMapping("/remove-many")
    ApiResponse<Integer> removeManyFromWatchList(@NotNull Long userId,@NotEmpty Long[] movieIds) {
        int removedCount = watchListService.removeManyFromWatchList(userId, movieIds);
        return ApiResponse.<Integer>builder()
                .message("Removed multiple movies from watchlist")
                .data(removedCount)
                .build();
    }

}
