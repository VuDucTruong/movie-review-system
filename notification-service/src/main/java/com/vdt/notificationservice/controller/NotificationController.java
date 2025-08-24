package com.vdt.notificationservice.controller;

import com.vdt.notificationservice.dto.ApiResponse;
import com.vdt.notificationservice.dto.BrevoEmailRequest.Recipient;
import com.vdt.notificationservice.dto.SendEmailRequest;
import com.vdt.notificationservice.event.NotificationEvent;
import com.vdt.notificationservice.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationController {

  EmailService emailService;

  @PostMapping("/send-email")
  ApiResponse<String> sendEmail(@RequestBody SendEmailRequest sendEmailRequest) {
    return ApiResponse.<String>builder()
        .data(emailService.sendEmail(sendEmailRequest))
        .message("Email sent successfully")
        .build();
  }

  @KafkaListener(topics = {"review-approved" , "sign-up-success"})
  void listenLikeYourReview(NotificationEvent event) {
    log.info("Received message: {}", event);
    emailService.sendEmail(
        SendEmailRequest.builder().subject(event.subject()).htmlContent(event.body()).to(
            Recipient.builder().email(event.recipient()).build()).build());

  }
}
