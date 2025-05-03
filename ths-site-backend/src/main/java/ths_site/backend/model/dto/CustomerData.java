package ths_site.backend.model.dto;

import ths_site.backend.model.UserData;

/* 
 * This function representents a recently created Customer without any sensitive data (id & createdAt). 
 */
public class CustomerData extends UserData {

  public CustomerData(String firstName, String lastName, String email, String password) {
    super(firstName, lastName, email, password);
  }

}
