package com.vdt.reviewservice.dto.response;

import java.time.LocalDateTime;

public record ReviewResponse(
    Long id,
    ProfileResponse user,
    Integer rating,
    String reviewText,
    boolean approved,
    Long totalLikes,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt
) {

}
