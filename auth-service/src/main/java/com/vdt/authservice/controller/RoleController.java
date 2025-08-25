package com.vdt.authservice.controller;

import com.vdt.authservice.dto.ApiResponse;
import com.vdt.authservice.dto.PageResponse;
import com.vdt.authservice.dto.request.RoleRequest;
import com.vdt.authservice.dto.response.RoleResponse;
import com.vdt.authservice.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleController {

  RoleService roleService;

  @GetMapping
  ApiResponse<PageResponse<RoleResponse>> getAllRoles(Pageable pageable) {
    return ApiResponse.<PageResponse<RoleResponse>>builder()
        .data(PageResponse.fromPage(roleService.getAllRoles(pageable)))
        .build();
  }

  @GetMapping("/{roleId}")
  ApiResponse<RoleResponse> getRoleById(@PathVariable Long roleId) {
    return ApiResponse.<RoleResponse>builder()
        .data(roleService.getRoleById(roleId))
        .build();
  }


  @PostMapping
  ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
    return ApiResponse.<RoleResponse>builder().data(roleService.createRole(roleRequest)).build();
  }

  @PatchMapping("/{roleId}")
  ApiResponse<RoleResponse> updateRole(@PathVariable Long roleId,
      @RequestBody RoleRequest roleRequest) {
    return ApiResponse.<RoleResponse>builder()
        .data(roleService.updateRole(roleId, roleRequest))
        .build();
  }

  @DeleteMapping("/{roleId}")
  ApiResponse<Void> deleteRole(@PathVariable Long roleId) {
    roleService.deleteRole(roleId);
    return ApiResponse.<Void>builder().message("Role deleted successfully").build();
  }

}
