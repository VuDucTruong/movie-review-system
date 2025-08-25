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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProfileController {
    ProfileService profileService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN_READ')")
    ApiResponse<ProfileResponse> getProfileByUserId(@PathVariable Long userId) {
        return ApiResponse.<ProfileResponse>builder()
                .data(profileService.getUserProfile(userId))
                .build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER_READ')")
    ApiResponse<ProfileResponse> getMe() {
        return ApiResponse.<ProfileResponse>builder()
                .data(profileService.getMe())
                .build();
    }


    @PostMapping(consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyRole('USER_CREATE', 'ADMIN_CREATE')")
    ApiResponse<ProfileResponse> addProfile(@ModelAttribute @Valid CreateProfileRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .data(profileService.createUserProfile(request))
                .build();
    }

    @PatchMapping(consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyRole('USER_UPDATE', 'ADMIN_UPDATE')")
    ApiResponse<ProfileResponse> updateProfile(@ModelAttribute @Valid UpdateProfileRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .data(profileService.updateUserProfile(request))
                .build();
    }

}
