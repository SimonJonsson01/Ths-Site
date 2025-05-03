package ths_site.backend.model.dto;

public class JobDto {
  private final String title;
  private final String content;
  private final CustomerDto customer;

  public JobDto(String title, String content, CustomerDto customer) {
    this.title = title;
    this.content = content;
    this.customer = customer;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

  public CustomerDto getCustomer() {
    return customer;
  }
}
