package com.vdt.authservice.dto.response;

import com.vdt.authservice.constant.Permission;
import java.util.Set;

public record RoleResponse(
    Long id,
    String name,
    Set<Permission> permissions
) {

}
