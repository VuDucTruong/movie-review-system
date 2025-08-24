package com.vdt.notificationservice.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Setter
@Builder
public class BrevoEmailRequest {

  Sender sender;
  List<Recipient> to;
  String subject;
  String htmlContent;

  @Builder
  public record Sender(String name, String email) {

  }


  @Builder
  public record Recipient(String name, String email) {

  }
}
