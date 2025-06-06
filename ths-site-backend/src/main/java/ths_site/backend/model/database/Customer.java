package ths_site.backend.model.database;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import ths_site.backend.model.User;
import ths_site.backend.model.dto.UserData;

@Entity
@Table(name = "customer")
public class Customer extends User {

  public Customer() {
    super();
  }

  public Customer(UUID id, String first_name, String last_name, String email, String password) {
    super(id, first_name, last_name, email, password);
  }

  public Customer(UserData userData) {
    this();
    this.id = UUID.randomUUID();
    this.firstName = userData.getFirstName();
    this.lastName = userData.getLastName();
    this.email = userData.getEmail();
    this.password = userData.getPassword();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
  }

  @Override
  public String getUsername() {
    return email;
  }
}
