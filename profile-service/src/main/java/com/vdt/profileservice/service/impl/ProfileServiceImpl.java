package com.vdt.profileservice.service.impl;

import com.vdt.profileservice.dto.CreateProfileRequest;
import com.vdt.profileservice.dto.ProfileResponse;
import com.vdt.profileservice.dto.UpdateProfileRequest;
import com.vdt.profileservice.entity.Profile;
import com.vdt.profileservice.exception.AppException;
import com.vdt.profileservice.exception.ErrorCode;
import com.vdt.profileservice.mapper.ProfileMapper;
import com.vdt.profileservice.repository.ProfileRepository;
import com.vdt.profileservice.repository.client.FileClient;
import com.vdt.profileservice.service.ProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  ProfileRepository profileRepository;
  FileClient fileClient;
  ProfileMapper profileMapper;

  @Override
  public ProfileResponse createUserProfile(CreateProfileRequest request) {
    Profile profile = profileMapper.fromCreateRequest(request);
    var url = fileClient.uploadFile(request.avatar()).getData().url();

    profile.setAvatarUrl(url);

    return profileMapper.toProfileResponse(profileRepository.save(profile));

  }

  @Override
  public ProfileResponse updateUserProfile(UpdateProfileRequest request) {
    Profile profile = profileRepository.findById(request.userId())
        .orElseThrow(() -> new AppException(
            ErrorCode.USER_NOT_FOUND));
    profileMapper.updateProfileFromRequest(request, profile);

    if (request.avatar() != null) {
      var url = fileClient.uploadFile(request.avatar()).getData().url();
      profile.setAvatarUrl(url);
    }
    return profileMapper.toProfileResponse(profileRepository.save(profile));
  }

  @Override
  public ProfileResponse getUserProfile(Long userId) {
    Profile profile = profileRepository.findById(userId).orElseThrow(() -> new AppException(
        ErrorCode.USER_NOT_FOUND));
    return profileMapper.toProfileResponse(profile);
  }

  @Override
  public ProfileResponse getMe() {
    Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    Long userId = Long.parseLong(jwt.getSubject());
    Profile profile = profileRepository.findById(userId).orElseThrow(() -> new AppException(
        ErrorCode.USER_NOT_FOUND));
    return profileMapper.toProfileResponse(profile);
  }
}
