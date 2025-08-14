package com.vdt.authservice.dto;

public record Token(
        String accessToken,
        String refreshToken
) {
}
