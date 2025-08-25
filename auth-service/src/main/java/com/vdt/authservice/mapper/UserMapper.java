package com.vdt.authservice.mapper;

import com.vdt.authservice.dto.Token;
import com.vdt.authservice.dto.request.CreateUserRequest;
import com.vdt.authservice.dto.request.RegisterRequest;
import com.vdt.authservice.dto.response.UserResponse;
import com.vdt.authservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    UserResponse toUserResponse(User user, Token token);


    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User fromRegisterRequest(RegisterRequest registerRequest);


    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User fromCreateUserRequest(CreateUserRequest createUserRequest);
}
