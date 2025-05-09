package ths_site.backend.model.dto;

/* 
 * This class representent a User as a DTO that will be sent to the frontend from the backend.
 */
public class UserDto {
  private final String firstName;
  private final String lastName;

  public UserDto(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName(){
    return firstName;
  }
  
  public String getLastName(){
    return lastName;
  }

}
