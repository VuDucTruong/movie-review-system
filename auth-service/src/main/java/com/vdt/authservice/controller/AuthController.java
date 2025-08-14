package com.vdt.authservice.controller;


import com.vdt.authservice.dto.ApiResponse;
import com.vdt.authservice.dto.Token;
import com.vdt.authservice.dto.request.LoginRequest;
import com.vdt.authservice.dto.request.RegisterRequest;
import com.vdt.authservice.dto.response.UserResponse;
import com.vdt.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {
    AuthService authService;

    @PostMapping("/login")
    ApiResponse<UserResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ApiResponse.<UserResponse>builder().data(authService.login(loginRequest)).build();
    }

    @PostMapping("/register")
    ApiResponse<UserResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return ApiResponse.<UserResponse>builder()
                .data(authService.register(registerRequest))
                .build();
    }

    @PostMapping("/token/refresh")
    ApiResponse<Token> refreshToken(@RequestBody String refreshToken) {
        return ApiResponse.<Token>builder().data(authService.refreshToken(refreshToken)).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout() {
        authService.logout();
        return ApiResponse.<Void>builder().message("Logout successfully").build();
    }

    // TODO: Change password --> Need Notification service

//    @PostMapping("/change-password/otp")
//    ApiResponse<Void> sendOtp(@RequestBody @Valid SendOtpRequest sendOtpRequest) {
//
//    }
//
//    @PostMapping("/change-password/verify")
//    ApiResponse<Void> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
//        authService.changePassword(changePasswordRequest);
//        return ApiResponse.<Void>builder().message("Change password successfully").build();
//    }


}
