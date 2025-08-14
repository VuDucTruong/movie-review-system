package com.vdt.authservice.service;

import com.vdt.authservice.dto.Token;
import com.vdt.authservice.dto.request.ChangePasswordRequest;
import com.vdt.authservice.dto.request.LoginRequest;
import com.vdt.authservice.dto.request.RegisterRequest;
import com.vdt.authservice.dto.response.UserResponse;

public interface AuthService {
    UserResponse login(LoginRequest loginRequest);
    UserResponse register(RegisterRequest registerRequest);
    void logout();
    Token refreshToken(String refreshToken);
    void changePassword(ChangePasswordRequest changePasswordRequest);
}
