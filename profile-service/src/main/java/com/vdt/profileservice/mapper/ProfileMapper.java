package com.vdt.profileservice.mapper;

import com.vdt.profileservice.dto.CreateProfileRequest;
import com.vdt.profileservice.dto.ProfileResponse;
import com.vdt.profileservice.dto.UpdateProfileRequest;
import com.vdt.profileservice.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    @Mapping(target = "watchLists", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    Profile fromCreateRequest(CreateProfileRequest request);

    @Mapping(target = "watchLists", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "avatarUrl", ignore = true)
    //@BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileFromRequest(UpdateProfileRequest request, @MappingTarget Profile profile);


    ProfileResponse toProfileResponse(Profile profile);
}
