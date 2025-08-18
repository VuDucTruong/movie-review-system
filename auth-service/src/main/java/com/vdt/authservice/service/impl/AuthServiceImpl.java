package com.vdt.authservice.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.vdt.authservice.config.security.JwtProperties;
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
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

  UserRepository userRepository;
  InvalidTokenRepository invalidTokenRepository;
  UserMapper userMapper;
  PasswordEncoder passwordEncoder;
  JwtProperties jwtProperties;


  @Override
  public UserResponse login(LoginRequest loginRequest) {
    User authenticatedUser = userRepository.findUserByEmail(loginRequest.email())
        .filter(user -> passwordEncoder.matches(loginRequest.password(),
            user.getPassword()))
        .orElseThrow(() -> new AppException(ErrorCode.LOGIN_FAIL));

    var token = new Token(
        generateToken(authenticatedUser, false),
        generateToken(authenticatedUser, true)
    );

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
    Jwt jwt = getJwt();

    String accessToken = jwt.getTokenValue();

    if (accessToken != null) {
      invalidTokenRepository.saveInvalidToken(accessToken,
          Math.max(Objects.requireNonNull(jwt.getExpiresAt()).toEpochMilli() - Instant.now().toEpochMilli(), 100));
    } else {
      throw new AppException(ErrorCode.LOGOUT_FAIL);
    }
  }

  @Override
  public Token refreshAccessToken(String refreshToken) {

    Long userId = Long.parseLong(getJwt().getSubject());

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

    String newAccessToken = generateToken(user, false);

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


  private Jwt getJwt() {
    log.warn(SecurityContextHolder.getContext().getAuthentication().toString());
    return (Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials();
  }



  public String generateToken(User user, boolean isRefreshToken) {
    long expTime = jwtProperties.getExpiration() * (isRefreshToken ? 10 : 1);
    //long expTime = isRefreshToken ? jwtProperties.getExpiration() * 10 : 1000;
    JWTClaimsSet claims = new JWTClaimsSet.Builder()
        .subject(user.getId().toString())
        .issuer("VDT")
        .issueTime(new Date())
        .claim("type", isRefreshToken ? "refresh" : "access")
        .expirationTime(new Date(System.currentTimeMillis() + expTime))
        .claim("roles", isRefreshToken ? null : user.getRoleString())
        .build();

    SignedJWT signedJWT = new SignedJWT(
        new JWSHeader(JWSAlgorithm.HS256),
        claims
    );

    try {
      signedJWT.sign(new MACSigner(jwtProperties.getSecret()));
    } catch (JOSEException e) {
      throw new AppException(ErrorCode.GENERATE_TOKEN_FAIL);
    }

    return signedJWT.serialize();
  }
}
