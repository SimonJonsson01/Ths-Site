package ths_site.backend.model.dto;

public class LoginResponse {
  private final String token;

  //private final int expiresIn;

  public LoginResponse(String token /*, int expiresIn */) {
    this.token = token;
    //this.expiresIn = expiresIn;
  }

  public String getToken(){
    return token;
  }

  /* public int getExpiration() {
    return expiresIn;
  } */
}
