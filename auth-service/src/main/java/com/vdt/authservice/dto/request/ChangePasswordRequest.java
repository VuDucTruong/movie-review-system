package com.vdt.authservice.dto.request;

import com.vdt.authservice.validator.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@PasswordMatches
public record ChangePasswordRequest(


        @Email(message = "INVALID_EMAIL")
        String email,
        @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9\\s])[\\S]{6,40}", message = "INVALID_PASSWORD")
        String newPassword,
        @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9\\s])[\\S]{6,40}", message = "INVALID_PASSWORD")
        String confirmPassword
) {
}
