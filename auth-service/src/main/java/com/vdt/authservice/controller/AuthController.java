package com.vdt.authservice.controller;


import com.vdt.authservice.dto.ApiResponse;
import com.vdt.authservice.dto.PageResponse;
import com.vdt.authservice.dto.Token;
import com.vdt.authservice.dto.request.AssignRoleRequest;
import com.vdt.authservice.dto.request.ChangePasswordRequest;
import com.vdt.authservice.dto.request.CreateUserRequest;
import com.vdt.authservice.dto.request.LoginRequest;
import com.vdt.authservice.dto.request.RegisterRequest;
import com.vdt.authservice.dto.request.SendOtpRequest;
import com.vdt.authservice.dto.response.UserResponse;
import com.vdt.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {

  AuthService authService;

  @GetMapping("/test")
  ApiResponse<Void> test() {
    authService.checkCredentials();
    return ApiResponse.<Void>builder().message("OK").build();
  }

  @GetMapping("/all")
  ApiResponse<PageResponse<UserResponse>> getAllUsers(Pageable pageable) {
    return ApiResponse.<PageResponse<UserResponse>>builder()
        .data(PageResponse.fromPage(authService.getAllUsers(pageable)))
        .build();
  }

  @PostMapping("/me/logout")
  ApiResponse<Void> logout() {
    authService.logout();
    return ApiResponse.<Void>builder().message("Logout successfully").build();
  }


  @PostMapping("/login")
  ApiResponse<UserResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    return ApiResponse.<UserResponse>builder().data(authService.login(loginRequest)).build();
  }

  @PostMapping(value = "/register")
  ApiResponse<UserResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
    return ApiResponse.<UserResponse>builder()
        .data(authService.register(registerRequest))
        .build();
  }

  @PostMapping(value = "/create")
  @PreAuthorize("hasRole('ADMIN_CREATE')")
  ApiResponse<UserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
    return ApiResponse.<UserResponse>builder()
        .data(authService.createUser(createUserRequest))
        .build();
  }


  @PostMapping("/refresh")
  ApiResponse<Token> refreshToken(@RequestBody String refreshToken) {
    return ApiResponse.<Token>builder()
        .data(authService.refreshAccessToken(refreshToken))
        .build();
  }


  @PostMapping("/introspect")
  ResponseEntity<Boolean> introspect(@RequestBody String accessToken) {
    return ResponseEntity.ok(authService.introspectAccessToken(accessToken));
  }

  @PutMapping("/assign-role")
  @PreAuthorize("hasRole('ADMIN_UPDATE')")
  ApiResponse<UserResponse> assignRoleToUser(@RequestBody AssignRoleRequest assignRoleRequest) {
    return ApiResponse.<UserResponse>builder()
        .data(authService.assignRoleToUser(assignRoleRequest))
        .build();
  }

  @PostMapping("/change-password/otp")
  ApiResponse<Void> sendOtp(@RequestBody @Valid SendOtpRequest sendOtpRequest) {
    authService.sendOtp(sendOtpRequest.email());
    return ApiResponse.<Void>builder()
        .message("Send OTP successfully")
        .build();
  }

  @PostMapping("/change-password/verify")
  ApiResponse<Void> changePassword(
      @RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
    authService.changePassword(changePasswordRequest);
    return ApiResponse.<Void>builder().message("Change password successfully").build();
  }


}
