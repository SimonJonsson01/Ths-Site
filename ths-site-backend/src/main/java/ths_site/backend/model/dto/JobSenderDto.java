package ths_site.backend.model.dto;

import java.util.UUID;

public class JobSenderDto {
  private final String title;
  private final String content;
  private final UUID customerId;

  public JobSenderDto(String title,
      String content, UUID customerId) {
    this.title = title;
    this.content = content;
    this.customerId = customerId;
  }

  public String getTitle() {
    return title;
  }
  
  public String getContent() {
    return content;
  }
  
  public UUID getCustomerId() {
    return customerId;
  }

}
