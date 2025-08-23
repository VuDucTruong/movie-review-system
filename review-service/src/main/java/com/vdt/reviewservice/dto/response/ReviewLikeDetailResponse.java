package com.vdt.reviewservice.dto.response;

import java.time.LocalDateTime;

public record ReviewLikeDetailResponse(
    ReviewResponse review,
    ProfileResponse user,
    boolean isLiked,
    LocalDateTime createdAt
) {

}
