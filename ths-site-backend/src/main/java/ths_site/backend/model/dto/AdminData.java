package ths_site.backend.model.dto;

import ths_site.backend.model.UserData;

public class AdminData extends UserData{

  public AdminData(String firstName, String lastName, String email, String password){
    super(firstName, lastName, email, password);
  }
}
