package ths_site.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ths_site.backend.model.database.Admin;
import ths_site.backend.model.database.Customer;
import ths_site.backend.model.dto.LoginDto;
import ths_site.backend.model.dto.LoginResponse;
import ths_site.backend.model.dto.UserData;
import ths_site.backend.model.dto.UserDto;
import ths_site.backend.service.AuthenticationService;
//import ths_site.backend.service.JWTService;

@EnableMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping(value = "/auth")
public class AccountController {

  private final AuthenticationService authService;
  //private final JWTService jwtService;

  // private static final Logger LOGGER =
  // LoggerFactory.getLogger("TEST-AccountController");

  public AccountController(AuthenticationService authService) {
    this.authService = authService;
  }

  @PostMapping(value = "/signup", produces = "application/json")
  public ResponseEntity<UserDto> signupCustomer(@RequestBody UserData userData) {
    Customer registeredCustomer = this.authService.signupCustomer(userData);

    return ResponseEntity.ok(registeredCustomer.toDto());
  }

  @PostMapping(value = "/signupAdmin", produces = "application/json")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<Admin> signupAdmin(@RequestBody UserData userData) {
    Admin registeredAdmin = this.authService.signupAdmin(userData);

    return ResponseEntity.ok(registeredAdmin);
  }

  /*
   * LoginResponse
   */
  @PostMapping(value = "/login", produces = "application/json")
  public ResponseEntity<LoginResponse> verify(@RequestBody LoginDto loginDto) {
    String jwtToken = this.authService.authenticate(loginDto);

    return ResponseEntity.ok(new LoginResponse(jwtToken));
  }

  /* @GetMapping("/testAuthentication")
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  public String test1(Authentication auth) {
    System.out.println("BEFORE RETURN");
    return "Authenticated as: " + (auth != null ? auth.getName() : "null");
  }

  @GetMapping("/testPreAuthorizeCustomer")
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  public String test2(Authentication auth) {
    return "Authenticated with role as: " + (auth != null ? auth.getName() : "null");
  }

  @GetMapping("/testPreAuthorizeAdmin")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public String test3(Authentication auth) {
    return "Authenticated with role as: " + (auth != null ? auth.getName() : "null");
  } */
}
