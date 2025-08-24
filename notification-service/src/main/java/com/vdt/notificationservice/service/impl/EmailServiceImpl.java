package com.vdt.notificationservice.service.impl;

import com.vdt.notificationservice.dto.BrevoEmailRequest;
import com.vdt.notificationservice.dto.BrevoEmailRequest.Sender;
import com.vdt.notificationservice.dto.SendEmailRequest;
import com.vdt.notificationservice.exception.AppException;
import com.vdt.notificationservice.exception.ErrorCode;
import com.vdt.notificationservice.repository.BrevoClient;
import com.vdt.notificationservice.service.EmailService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  BrevoClient brevoClient;

  @NonFinal
  @Value("${notification.email.brevo-api-key}")
  String apiKey;

  @NonFinal
  @Value("${notification.email.sender}")
  String senderEmail;


  @Override
  public String sendEmail(SendEmailRequest request) {
    var brevoEmailRequest = BrevoEmailRequest.builder()
        .sender(new Sender("Movie Review", senderEmail)).to(List.of(request.to()))
        .subject(request.subject())
        .htmlContent(request.htmlContent()).build();

    try {
      var response = brevoClient.sendEmail(apiKey, brevoEmailRequest);
      return response.messageId();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new AppException(ErrorCode.SEND_EMAIL_FAIL);
    }
  }
}
