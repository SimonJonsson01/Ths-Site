package ths_site.backend.model.dto;

import java.util.UUID;

public class ReviewSenderDto {
  private final String title;
  private final String content;
  private final int score;
  private final UUID customerId;

  public ReviewSenderDto(String title,
      String content,
      int score, UUID customerId) {
    this.title = title;
    this.content = content;
    this.score = score;
    this.customerId = customerId;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public int getScore() {
    return score;
  }

  public UUID getCustomerId() {
    return customerId;
  }

}
