package com.vdt.authservice.service;

import com.vdt.authservice.dto.Token;
import com.vdt.authservice.dto.request.AssignRoleRequest;
import com.vdt.authservice.dto.request.ChangePasswordRequest;
import com.vdt.authservice.dto.request.CreateUserRequest;
import com.vdt.authservice.dto.request.LoginRequest;
import com.vdt.authservice.dto.request.RegisterRequest;
import com.vdt.authservice.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthService {
    UserResponse login(LoginRequest loginRequest);
    UserResponse register(RegisterRequest registerRequest);
    UserResponse createUser(CreateUserRequest createUserRequest);
    void logout();
    Token refreshAccessToken(String refreshToken);
    void changePassword(ChangePasswordRequest changePasswordRequest);
    boolean introspectAccessToken(String accessToken);
    UserResponse assignRoleToUser(AssignRoleRequest assignRoleRequest);
    void sendOtp(String email);
    void checkCredentials();
    Page<UserResponse> getAllUsers(Pageable pageable);
}
