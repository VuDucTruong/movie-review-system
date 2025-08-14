package com.vdt.authservice.mapper;

import com.vdt.authservice.dto.request.RegisterRequest;
import com.vdt.authservice.dto.response.UserResponse;
import com.vdt.authservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "token", ignore = true)
    UserResponse toUserResponse(User user);


    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User fromRegisterRequest(RegisterRequest registerRequest);
}
