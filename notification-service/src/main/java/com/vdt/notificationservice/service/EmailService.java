package com.vdt.notificationservice.service;

import com.vdt.notificationservice.dto.SendEmailRequest;

public interface EmailService {

  String sendEmail(SendEmailRequest request);
}
