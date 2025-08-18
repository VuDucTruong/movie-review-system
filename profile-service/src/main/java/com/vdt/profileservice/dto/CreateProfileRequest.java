package com.vdt.profileservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public record CreateProfileRequest(
        @NotNull(message = "USER_ID_CANNOT_BE_NULL")
        Long userId,
        @Size(min = 3, max = 20, message = "INVALID_DISPLAY_NAME")
        @NotEmpty(message = "DISPLAY_NAME_CANNOT_BE_EMPTY")
        String displayName,
        @Size(max = 200, message = "INVALID_BIO")
        String bio,
        MultipartFile avatarImage,
        Map<String , Object> preferences
) {
}
