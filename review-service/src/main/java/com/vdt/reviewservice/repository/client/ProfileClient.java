package com.vdt.reviewservice.repository.client;


import com.vdt.reviewservice.dto.ApiResponse;
import com.vdt.reviewservice.dto.response.ProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "profile-service", url = "${app.services.profile-service.url}")
public interface ProfileClient {
  @GetMapping("/me")
  ApiResponse<ProfileResponse> getMe();
}
