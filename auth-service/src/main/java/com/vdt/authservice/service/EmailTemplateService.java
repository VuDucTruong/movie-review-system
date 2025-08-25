package com.vdt.authservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailTemplateService {
  TemplateEngine templateEngine;

  public String buildOtpEmail(String otp){
    Context context = new Context();
    context.setVariable("otp", otp);
    return templateEngine.process("otp-email", context);
  }

  public String buildWelcomeEmail(String name){
    Context context = new Context();
    context.setVariable("name", name);
    return templateEngine.process("welcome-email", context);
  }
}
