package com.vdt.authservice.service.impl;

import com.vdt.authservice.config.security.JwtHelper;
import com.vdt.authservice.dto.Token;
import com.vdt.authservice.dto.request.ChangePasswordRequest;
import com.vdt.authservice.dto.request.LoginRequest;
import com.vdt.authservice.dto.request.RegisterRequest;
import com.vdt.authservice.dto.response.UserResponse;
import com.vdt.authservice.entity.User;
import com.vdt.authservice.exception.AppException;
import com.vdt.authservice.exception.ErrorCode;
import com.vdt.authservice.mapper.UserMapper;
import com.vdt.authservice.repository.InvalidTokenRepository;
import com.vdt.authservice.repository.UserRepository;
import com.vdt.authservice.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    InvalidTokenRepository invalidTokenRepository;
    JwtHelper jwtHelper;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;


    @Override
    public UserResponse login(LoginRequest loginRequest) {
        User authenticatedUser = userRepository.findUserByEmail(loginRequest.email())
                .filter(user -> passwordEncoder.matches(loginRequest.password(),
                        user.getPassword()))
                .orElseThrow(() -> new AppException(ErrorCode.LOGIN_FAIL));

        var token = jwtHelper.createToken(authenticatedUser);

        return userMapper.toUserResponse(authenticatedUser, token);
    }

    @Override
    public UserResponse register(RegisterRequest registerRequest) {

        var user = userMapper.fromRegisterRequest(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toUserResponse(userRepository.save(user), null);
    }

    @Override
    public void logout() {

        String accessToken = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

        if (accessToken != null) {
            invalidTokenRepository.saveInvalidToken(accessToken,
                    jwtHelper.getRemainingExpTime(accessToken));
        } else {
            throw new AppException(ErrorCode.LOGOUT_FAIL);
        }
    }

    @Override
    public Token refreshAccessToken(String refreshToken) {
        Long userId = Long.parseLong(jwtHelper.getSubject(refreshToken));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        String newAccessToken = jwtHelper.generateToken(user, false);

        return new Token(newAccessToken, refreshToken);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        var user = userRepository.findUserByEmail(changePasswordRequest.email())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
        userRepository.save(user);
        logout();
    }
}
