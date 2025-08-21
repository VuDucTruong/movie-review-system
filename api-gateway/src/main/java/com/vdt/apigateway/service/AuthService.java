package com.vdt.apigateway.service;

import com.vdt.apigateway.repository.AuthClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
  AuthClient authClient;


  public Mono<ResponseEntity<Boolean>> introspectAccessToken(String accessToken) {
    return authClient.introspect(accessToken);
  }

}
