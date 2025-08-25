package com.vdt.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.util.Set;

public record CreateUserRequest(
    @Email(message = "INVALID_EMAIL")
    String email,
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9\\s])[\\S]{6,40}", message = "INVALID_PASSWORD")
    String password,
    Set<Long> roleIds
) {

}
