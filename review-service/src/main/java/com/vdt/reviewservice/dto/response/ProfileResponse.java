package com.vdt.reviewservice.dto.response;

import java.time.LocalDateTime;

public record ProfileResponse(
        String displayName,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        String bio,
        String avatarUrl,
        String email
) {
}
