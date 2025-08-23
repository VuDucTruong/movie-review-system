package com.vdt.reviewservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateReviewRequest(
    @NotNull(message = "MOVIE_REQUIRED")
    Long movieId,
    @Min(value = 1, message = "RATING_OUT_OF_BOUNDS")
    @Max(value = 5, message = "RATING_OUT_OF_BOUNDS")
    Integer rating,
    String reviewText
) {

}
