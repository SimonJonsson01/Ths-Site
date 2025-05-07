package ths_site.backend.model.dto;

public class ReviewDto {

  private final String title;

  private final String content;

  private final int score;

  private final UserDto customer;

  public ReviewDto(String title, String content, int score, UserDto customer) {
    this.title = title;
    this.content = content;
    this.score = score;
    this.customer = customer;
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

  public UserDto getCustomer() {
    return customer;
  }
}
