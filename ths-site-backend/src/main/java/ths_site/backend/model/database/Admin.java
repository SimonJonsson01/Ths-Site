package ths_site.backend.model.database;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import ths_site.backend.model.User;
import ths_site.backend.model.dto.AdminData;

@Entity
@Table(name = "admin")
public class Admin extends User {

  public Admin() {
    super();
  }

  public Admin(UUID id, String first_name, String last_name, String email, String password) {
    super(id, first_name, last_name, email, password);
  }

  public AdminData toData() {
    return new AdminData(this.getFirstName(), this.getLastName(), this.getEmail(), this.getPassword());
  }

}
