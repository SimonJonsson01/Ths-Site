package ths_site.backend.model.database;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import ths_site.backend.model.User;
import ths_site.backend.model.dto.CustomerData;
import ths_site.backend.model.dto.CustomerDto;

@Entity
@Table(name = "customer")
public class Customer extends User {

  public Customer() {
    super();
  }

  public Customer(UUID id, String first_name, String last_name, String email, String password) {
    super(id, first_name, last_name, email, password);
  }

  public CustomerDto toDto() {
    return new CustomerDto(this.getFirstName(), this.getLastName());
  }

  public CustomerData toData() {
    return new CustomerData(this.getFirstName(), this.getLastName(), this.getEmail(), this.getPassword());
  }

}
