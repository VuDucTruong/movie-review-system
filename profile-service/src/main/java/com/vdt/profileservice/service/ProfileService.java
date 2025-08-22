package com.vdt.profileservice.service;

import com.vdt.profileservice.dto.CreateProfileRequest;
import com.vdt.profileservice.dto.ProfileResponse;
import com.vdt.profileservice.dto.UpdateProfileRequest;

public interface ProfileService {
    ProfileResponse createUserProfile(CreateProfileRequest request);
    ProfileResponse updateUserProfile(UpdateProfileRequest request);
    ProfileResponse getUserProfile(Long userId);
    ProfileResponse getMe();
}
