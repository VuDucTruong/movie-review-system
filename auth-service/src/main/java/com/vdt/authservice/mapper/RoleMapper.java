package com.vdt.authservice.mapper;

import com.vdt.authservice.dto.request.RoleRequest;
import com.vdt.authservice.dto.response.RoleResponse;
import com.vdt.authservice.entity.Role;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

  RoleResponse toRoleResponse(Role role);

  @Mapping(target = "id", ignore = true)
  Role fromRoleRequest(RoleRequest roleRequest);


  @Mapping(target = "id", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  void updateRoleFromRequest(RoleRequest roleRequest, @MappingTarget Role role);
}
