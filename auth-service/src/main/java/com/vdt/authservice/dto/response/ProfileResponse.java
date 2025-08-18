package com.vdt.authservice.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ProfileResponse(
        String displayName,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        String bio,
        String avatarUrl,
        Map<String , Object> preferences
) {
}
