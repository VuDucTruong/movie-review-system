package com.vdt.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vdt.authservice.dto.Token;
import com.vdt.authservice.enums.Role;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        Long id,
        String email,
        Set<Role> roles,
        Token token
) {
}
