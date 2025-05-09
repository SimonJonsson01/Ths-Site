package ths_site.backend.model.dto;


/* 
 * This class represent a User with "surface-data". This can be used as a object between UserDto and User.
 */
public class UserData {

  private final String firstName;
  private final String lastName;
  private final String email;
  private final String password;

  public UserData(String firstName, String lastName, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

}
