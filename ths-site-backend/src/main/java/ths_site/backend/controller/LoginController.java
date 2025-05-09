package ths_site.backend.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ths_site.backend.model.database.Admin;
import ths_site.backend.model.database.Customer;
import ths_site.backend.model.dto.LoginDto;
import ths_site.backend.model.dto.UserData;
import ths_site.backend.service.AdminService;
import ths_site.backend.service.CustomerService;

@RestController
@RequestMapping("/login")
public class LoginController {

  private final CustomerService customerService;
  private final AdminService adminService;

  public LoginController(CustomerService customerService, AdminService adminService) {
    this.customerService = customerService;
    this.adminService = adminService;
  }

  @PostMapping(value = "/customer", produces = "application/json")
  public ResponseEntity<UserData> verifyCustomer(@RequestBody LoginDto loginDto) {
    Optional<Customer> opUser = this.customerService.getByEmail(loginDto.getEmail());
    if (opUser.isPresent()) {
      Customer customer = opUser.get();
      boolean verified = customer.getPassword().equals(loginDto.getPassword());
      if (verified) {
        return ResponseEntity.ok(customer.toData());
      } else {
        return ResponseEntity.badRequest().build();
      }
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping(value = "/admin", produces = "application/json")
  public ResponseEntity<UserData> verifyAdmin(@RequestBody LoginDto loginDto) {
    Optional<Admin> opUser = this.adminService.getByEmail(loginDto.getEmail());
    if (opUser.isPresent()) {
      Admin admin = opUser.get();
      boolean verified = admin.getPassword().equals(loginDto.getPassword());
      if (verified) {
        return ResponseEntity.ok(admin.toData());
      } else {
        return ResponseEntity.badRequest().build();
      }
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
