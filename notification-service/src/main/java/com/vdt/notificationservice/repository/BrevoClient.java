package com.vdt.notificationservice.repository;

import com.vdt.notificationservice.dto.BrevoEmailRequest;
import com.vdt.notificationservice.dto.BrevoEmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "brevo-client", url = "${notification.email.brevo-url}")
public interface BrevoClient {
  @PostMapping(value = "/v3/smtp/email", produces = MediaType.APPLICATION_JSON_VALUE)
  BrevoEmailResponse sendEmail(@RequestHeader("api-key") String apiKey,@RequestBody BrevoEmailRequest request);

}
