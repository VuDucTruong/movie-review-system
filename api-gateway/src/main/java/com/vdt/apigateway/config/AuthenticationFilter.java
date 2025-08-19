package com.vdt.apigateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdt.apigateway.dto.ApiResponse;
import com.vdt.apigateway.service.AuthService;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {

  AuthService authService;
  ObjectMapper objectMapper = new ObjectMapper();

  @Value("${api.prefix}")
  @NonFinal
  String apiPrefix;

  String[] publicPaths = {
    "/auth/login",
    "/auth/register",
    "/auth/refresh",
  };

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    log.info("Request path: {}", exchange.getRequest().getPath());
    if (isPublicEndpoint(exchange.getRequest())) {
      log.info("Public endpoint accessed: {}", exchange.getRequest().getPath());
      return chain.filter(exchange);
    }

    List<String> authHeaders = exchange.getRequest().getHeaders().getOrEmpty(
        HttpHeaders.AUTHORIZATION);
    if (authHeaders.isEmpty()) {
      return unauthenticated(exchange.getResponse());
    }

    String token = authHeaders.getFirst().replace("Bearer ", "");
    log.info("token: {}", token);

    try {
      if (Boolean.FALSE.equals(authService.introspectAccessToken(token))) {
        return unauthenticated(exchange.getResponse());
      }
    } catch (Exception e) {
      log.error("Error during token introspection: {}", e.getMessage());
      return unauthenticated(exchange.getResponse());
    }

    return chain.filter(exchange);
  }


  private boolean isPublicEndpoint(ServerHttpRequest request) {
    return Arrays.stream(publicPaths)
        .anyMatch(s -> request.getURI().getPath().matches(s));
  }


  Mono<Void> unauthenticated(ServerHttpResponse response) {
    ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
        .code(4011)
        .message("Unauthenticated in API Gateway")
        .build();
    String body = "";
    try {
      body = objectMapper.writeValueAsString(apiResponse);
    } catch (JsonProcessingException e) {
      log.error("Error serializing response body: {}", e.getMessage());
    }

    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

    return response.writeWith(
        Mono.just(response.bufferFactory().wrap(body.getBytes())));
  }

  @Override
  public int getOrder() {
    return -1;
  }
}
