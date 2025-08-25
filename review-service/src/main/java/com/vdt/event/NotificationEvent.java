package com.vdt.event;

import java.util.Map;
import lombok.Builder;

@Builder
public record NotificationEvent(
    String channel,
    String recipient,
    String templateCode,
    Map<String, Object> param,
    String subject,
    String body
) {

}
