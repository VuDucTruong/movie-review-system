package com.vdt.reviewservice.dto.response;

public record ReviewStatisticResponse(
    long review5Count,
    long review4Count,
    long review3Count,
    long review2Count,
    long review1Count,
    long averageRating,
    long totalReviews
) {

}
