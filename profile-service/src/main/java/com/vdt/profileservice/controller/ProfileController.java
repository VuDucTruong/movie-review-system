package com.vdt.profileservice.controller;

import com.vdt.profileservice.dto.ApiResponse;
import com.vdt.profileservice.dto.CreateProfileRequest;
import com.vdt.profileservice.dto.ProfileResponse;
import com.vdt.profileservice.dto.UpdateProfileRequest;
import com.vdt.profileservice.service.ProfileService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProfileController {
    ProfileService profileService;

    @GetMapping("/{userId}")
    ApiResponse<ProfileResponse> getProfileByUserId(@PathVariable Long userId) {
        return ApiResponse.<ProfileResponse>builder()
                .data(profileService.getUserProfile(userId))
                .build();
    }


    @PostMapping(consumes = {"multipart/form-data"})
    ApiResponse<ProfileResponse> addProfile(@ModelAttribute @Valid CreateProfileRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .data(profileService.createUserProfile(request))
                .build();
    }

    @PatchMapping(consumes = {"multipart/form-data"})
    ApiResponse<ProfileResponse> updateProfile(@ModelAttribute @Valid UpdateProfileRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .data(profileService.updateUserProfile(request))
                .build();
    }

}
