package com.vdt.apigateway.repository;



import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface AuthClient {
  @PostExchange(url = "/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
  Mono<ResponseEntity<Boolean>> introspect(@RequestBody String accessToken);
}
