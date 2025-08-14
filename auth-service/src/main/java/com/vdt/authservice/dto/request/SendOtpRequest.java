package com.vdt.authservice.dto.request;

import jakarta.validation.constraints.Email;

public record SendOtpRequest(
        @Email(message = "INVALID_EMAIL")
        String email
) {
}
