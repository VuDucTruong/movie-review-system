package com.vdt.authservice.dto.request;

import com.vdt.authservice.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

public record RegisterRequest(
        @Email(message = "INVALID_EMAIL")
        String email,
        @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9\\s])[\\S]{6,40}", message = "INVALID_PASSWORD")
        String password,
        Set<Role> roles
) {
}
