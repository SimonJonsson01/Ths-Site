package ths_site.backend.model.dto;

import java.time.LocalDateTime;

public class JobDto {
  private final String title;
  private final String content;
  private final LocalDateTime createdAt;
  private final UserDto customer;
  private final boolean completed;

  public JobDto(String title, String content, LocalDateTime createdAt, UserDto customer, boolean completed) {
    this.title = title;
    this.content = content;
    this.createdAt = createdAt;
    this.customer = customer;
    this.completed = completed;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public UserDto getCustomer() {
    return customer;
  }

  public boolean getCompleted() {
    return completed;
  }
}
