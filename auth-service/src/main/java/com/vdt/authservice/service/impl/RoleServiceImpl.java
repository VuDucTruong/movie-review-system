package com.vdt.authservice.service.impl;

import com.vdt.authservice.dto.request.RoleRequest;
import com.vdt.authservice.dto.response.RoleResponse;
import com.vdt.authservice.exception.AppException;
import com.vdt.authservice.exception.ErrorCode;
import com.vdt.authservice.mapper.RoleMapper;
import com.vdt.authservice.repository.RoleRepository;
import com.vdt.authservice.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  RoleRepository roleRepository;
  RoleMapper roleMapper;

  @Override
  public RoleResponse createRole(RoleRequest roleRequest) {
    var role = roleMapper.fromRoleRequest(roleRequest);

    var savedRole = roleRepository.save(role);
    return roleMapper.toRoleResponse(savedRole);
  }

  @Override
  public RoleResponse getRoleById(Long roleId) {
    return roleRepository.findById(roleId).map(roleMapper::toRoleResponse)
        .orElseThrow(() -> new AppException(
            ErrorCode.ROLE_NOT_FOUND));
  }

  @Override
  public RoleResponse updateRole(Long roleId , RoleRequest roleRequest) {
    var role = roleRepository.findById(roleId)
        .orElseThrow(() -> new AppException(
            ErrorCode.ROLE_NOT_FOUND));

    roleMapper.updateRoleFromRequest(roleRequest, role);

    var updatedRole = roleRepository.save(role);
    return roleMapper.toRoleResponse(updatedRole);
  }

  @Override
  public void deleteRole(Long roleId) {
    roleRepository.deleteById(roleId);
  }

  @Override
  public Page<RoleResponse> getAllRoles(Pageable pageable) {
    return roleRepository.findAll(pageable)
        .map(roleMapper::toRoleResponse);
  }
}
