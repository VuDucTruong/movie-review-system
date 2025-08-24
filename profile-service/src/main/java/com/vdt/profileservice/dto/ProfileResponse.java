package com.vdt.profileservice.dto;

import java.time.LocalDateTime;

public record ProfileResponse(
    String displayName,
    String email,
    LocalDateTime createdAt,
    LocalDateTime modifiedAt,
    String bio,
    String avatarUrl
) {

}
