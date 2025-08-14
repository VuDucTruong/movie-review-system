package com.vdt.authservice.dto.response;

import com.vdt.authservice.dto.Token;
import com.vdt.authservice.enums.Role;

import java.util.Set;

public record UserResponse(
        Long id,
        String email,
        Set<Role> roles,
        Token token
) {
}
