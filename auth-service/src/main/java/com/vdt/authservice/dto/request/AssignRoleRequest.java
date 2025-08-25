package com.vdt.authservice.dto.request;

import java.util.Set;

public record AssignRoleRequest(
    Long userId,
    Set<Long> roleIds
) {

}
