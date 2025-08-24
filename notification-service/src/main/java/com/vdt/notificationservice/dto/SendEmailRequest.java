package com.vdt.notificationservice.dto;

import com.vdt.notificationservice.dto.BrevoEmailRequest.Recipient;
import lombok.Builder;

@Builder
public record SendEmailRequest(
    Recipient to,
    String subject,
    String htmlContent
) {

}
