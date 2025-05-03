package ths_site.backend.model.dto;

public class AdminDto {
  private final String firstName;
  private final String lastName;

  public AdminDto(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }
}

