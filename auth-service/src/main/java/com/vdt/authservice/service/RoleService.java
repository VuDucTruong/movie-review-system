package com.vdt.authservice.service;

import com.vdt.authservice.dto.request.RoleRequest;
import com.vdt.authservice.dto.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
  RoleResponse createRole(RoleRequest roleRequest);
  RoleResponse getRoleById(Long roleId);
  RoleResponse updateRole(Long roleId, RoleRequest roleRequest);
  void deleteRole(Long roleId);
  Page<RoleResponse> getAllRoles(Pageable pageable);
}
