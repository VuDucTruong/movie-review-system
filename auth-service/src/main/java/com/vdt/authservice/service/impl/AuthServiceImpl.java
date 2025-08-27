package com.vdt.authservice.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.vdt.authservice.dto.JwtProperties;
import com.vdt.authservice.dto.Token;
import com.vdt.authservice.dto.request.AssignRoleRequest;
import com.vdt.authservice.dto.request.ChangePasswordRequest;
import com.vdt.authservice.dto.request.CreateUserRequest;
import com.vdt.authservice.dto.request.LoginRequest;
import com.vdt.authservice.dto.request.RegisterRequest;
import com.vdt.authservice.dto.response.UserResponse;
import com.vdt.authservice.entity.User;
import com.vdt.authservice.repository.RoleRepository;
import com.vdt.authservice.service.EmailTemplateService;
import com.vdt.event.NotificationEvent;
import com.vdt.authservice.exception.AppException;
import com.vdt.authservice.exception.ErrorCode;
import com.vdt.authservice.mapper.UserMapper;
import com.vdt.authservice.repository.CacheRepository;
import com.vdt.authservice.repository.UserRepository;
import com.vdt.authservice.service.AuthService;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

  UserRepository userRepository;
  CacheRepository cacheRepository;
  RoleRepository roleRepository;

  UserMapper userMapper;
  PasswordEncoder passwordEncoder;
  JwtProperties jwtProperties;
  KafkaTemplate<String, Object> kafkaTemplate;
  EmailTemplateService emailTemplateService;
  Random rand = new Random();

  @Override
  public UserResponse login(LoginRequest loginRequest) {
    User authenticatedUser = userRepository.findUserByEmail(loginRequest.email())
        .filter(user -> passwordEncoder.matches(loginRequest.password(),
            user.getPassword()))
        .orElseThrow(() -> new AppException(ErrorCode.LOGIN_FAIL));

    if(Boolean.FALSE.equals(authenticatedUser.getIsActive())) {
      throw new AppException(ErrorCode.ACCOUNT_DISABLED);
    }

    var token = new Token(
        generateToken(authenticatedUser, false),
        generateToken(authenticatedUser, true)
    );

    return userMapper.toUserResponse(authenticatedUser, token);
  }

  @Override
  public UserResponse register(RegisterRequest registerRequest) {

    if(userRepository.existsByEmail(registerRequest.email())) {
      throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
    }

    var user = userMapper.fromRegisterRequest(registerRequest);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    var response =  userMapper.toUserResponse(userRepository.save(user), null);

    var notificationEvent = NotificationEvent.builder().channel("EMAIL").recipient(user.getEmail())
        .subject("Sign up successful")
        .body(emailTemplateService.buildWelcomeEmail(user.getEmail())).build();
    kafkaTemplate.send("sign-up-success", notificationEvent);


    return response;
  }

  @Override
  public UserResponse createUser(CreateUserRequest createUserRequest) {
    if(userRepository.existsByEmail(createUserRequest.email())) {
      throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
    }

    var user = userMapper.fromCreateUserRequest(createUserRequest);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    var roles = roleRepository.findByIdIn(createUserRequest.roleIds());

    if(roles.size() != createUserRequest.roleIds().size()) {

      var missingRoleIds = createUserRequest.roleIds().stream()
          .filter(roleId -> roles.stream().noneMatch(role -> role.getId().equals(roleId)))
          .toArray();

      throw new AppException(ErrorCode.ROLE_NOT_FOUND, StringUtils.arrayToCommaDelimitedString(missingRoleIds));
    }
    return userMapper.toUserResponse(userRepository.save(user), null);
  }

  @Override
  public void logout() {
    Jwt jwt = getJwt();

    String accessToken = jwt.getTokenValue();

    if (accessToken != null) {
      cacheRepository.saveInvalidToken(accessToken,
          Math.max(Objects.requireNonNull(jwt.getExpiresAt()).toEpochMilli() - Instant.now()
              .toEpochMilli(), 100));
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
  public void sendOtp(String email) {

    if(!userRepository.existsByEmail(email)) {
      throw new AppException(ErrorCode.USER_NOT_FOUND);
    }

    int otp = 100000 + rand.nextInt(900000);
    String otpString = String.valueOf(otp);
    cacheRepository.saveOtp(email, otpString, 1);
    var notificationEvent = NotificationEvent.builder().channel("EMAIL").recipient(email)
        .subject("OTP for Change Password")
        .body(emailTemplateService.buildOtpEmail(otpString)).build();
    kafkaTemplate.send("send-otp", notificationEvent);
  }

  @Override
  public void changePassword(ChangePasswordRequest changePasswordRequest) {
    var user = userRepository.findUserByEmail(changePasswordRequest.email())
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

    String otp = cacheRepository.getOtp(user.getEmail());
    if(!otp.equals(changePasswordRequest.otp())) {
      throw new AppException(ErrorCode.INVALID_OTP);
    }

    user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
    userRepository.save(user);
    logout();
  }

  @Override
  public boolean introspectAccessToken(String accessToken) {

    if (cacheRepository.isInvalidToken(accessToken)) {
      log.warn("Invalid access token: {}", accessToken);
      return false;
    }
    try {
      SignedJWT signedJWT = SignedJWT.parse(accessToken);
      // Validate the JWT signature
      JWSVerifier verifier = new MACVerifier(jwtProperties.getSecret());
      if (!signedJWT.verify(verifier)) {
        throw new JwtException("Invalid JWT signature");
      }
      // Validate the JWT claims
      Date exp = signedJWT.getJWTClaimsSet().getExpirationTime();
      if (exp != null && exp.before(new Date())) {
        throw new JwtException("JWT token is expired");
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public UserResponse assignRoleToUser(AssignRoleRequest assignRoleRequest) {
    var roles = roleRepository.findByIdIn(assignRoleRequest.roleIds());

    if(roles.size() != assignRoleRequest.roleIds().size()) {

      var missingRoleIds = assignRoleRequest.roleIds().stream()
          .filter(roleId -> roles.stream().noneMatch(role -> role.getId().equals(roleId)))
          .toArray();

      throw new AppException(ErrorCode.ROLE_NOT_FOUND, StringUtils.arrayToCommaDelimitedString(missingRoleIds));
    }

    var user = userRepository.findById(assignRoleRequest.userId())
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

    user.setRoles(roles);

    return userMapper.toUserResponse(userRepository.save(user), null);
  }


  @Override
  public void checkCredentials() {
    log.warn(SecurityContextHolder.getContext().getAuthentication().getName());
    log.warn(SecurityContextHolder.getContext().getAuthentication().toString());
  }

  @Override
  public Page<UserResponse> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable)
        .map(user -> userMapper.toUserResponse(user, null));
  }

  private Jwt getJwt() {
    log.warn(SecurityContextHolder.getContext().getAuthentication().toString());
    return (Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials();
  }


  private String generateToken(User user, boolean isRefreshToken) {
    long expTime = jwtProperties.getExpiration() * (isRefreshToken ? 10 : 1);
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
