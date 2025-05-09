package ths_site.backend.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import ths_site.backend.model.dto.UserData;
import ths_site.backend.model.dto.UserDto;

@MappedSuperclass
public abstract class User implements UserDetails {

  @Id
  protected UUID id;

  @Column(name = "first_name")
  protected String firstName;

  @Column(name = "last_name")
  protected String lastName;

  @Column(unique = true)
  protected String email;

  protected String password;

  @Column(name = "created_at")
  protected LocalDateTime createdAt;

  public User() {
    this.createdAt = LocalDateTime.now();
  }

  public User(UUID id, String first_name, String last_name, String email, String password) {
    this();
    this.id = id;
    this.firstName = first_name;
    this.lastName = last_name;
    this.email = email;
    this.password = password;
  }

  public User(UserData userData) {
    this();
    this.id = UUID.randomUUID();
    this.firstName = userData.getFirstName();
    this.lastName = userData.getLastName();
    this.email = userData.getEmail();
    this.password = userData.getPassword();
  }

  public void setRandomId() {
    this.id = UUID.randomUUID();
  }

  public UUID getId() {
    return id;
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

  @Override
  public String getPassword() {
    return password;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserData toData() {
    return new UserData(firstName, lastName, email, password);
  }

  public UserDto toDto() {
    return new UserDto(firstName, lastName);
  }

}
