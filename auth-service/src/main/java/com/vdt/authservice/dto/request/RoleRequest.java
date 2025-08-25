package com.vdt.authservice.dto.request;

import com.vdt.authservice.constant.Permission;
import java.util.Set;

public record RoleRequest(
    String name,
    Set<Permission> permissions
) {

}
