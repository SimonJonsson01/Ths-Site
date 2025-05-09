package ths_site.backend.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ths_site.backend.model.User;
import ths_site.backend.model.database.Admin;
import ths_site.backend.model.database.Customer;
import ths_site.backend.model.dto.LoginDto;
import ths_site.backend.model.dto.UserData;
import ths_site.backend.repository.AdminRepository;
import ths_site.backend.repository.CustomerRepository;

@Service
public class AuthenticationService {

  private final AdminRepository adminRepository;
  private final AuthenticationManager authManager;
  private final CustomerRepository customerRepository;
  private final JWTService jwtService;
  private final PasswordEncoder passwordEncoder;

  public AuthenticationService(AdminRepository adminRepository, AuthenticationManager authManager,
      CustomerRepository customerRepository, JWTService jwtService, PasswordEncoder passwordEncoder) {
    this.adminRepository = adminRepository;
    this.authManager = authManager;
    this.customerRepository = customerRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  public Customer signupCustomer(UserData customerData) {
    Customer user = new Customer(customerData);
    user.setPassword(passwordEncoder.encode(customerData.getPassword()));
    return this.customerRepository.save(user);
  }

  public Admin signupAdmin(UserData adminData) {
    Admin user = new Admin(adminData);
    user.setPassword(passwordEncoder.encode(adminData.getPassword()));
    return this.adminRepository.save(user);
  }

  public String authenticate(LoginDto loginDto) {
    Authentication auth = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginDto.getEmail(),
            loginDto.getPassword()));

    if (auth.isAuthenticated()) {
      User user = this.findIdByEmail(loginDto.getEmail());
      SecurityContextHolder.getContext().setAuthentication(auth);
      String jwtString = this.jwtService.generateToken(auth, user);
      return jwtString;
    } else {
      throw new Error();
    }
  }

  public User findIdByEmail(String email) {
    Optional<Customer> opCustomer = this.customerRepository.findByEmail(email);
    if (opCustomer.isPresent()) {
      Customer user = opCustomer.get();
      
      return user;
    }
    Optional<Admin> opAdmin = this.adminRepository.findByEmail(email);
    if (opAdmin.isPresent()) {
      Admin user = opAdmin.get();

      return user;
    }
    throw new Error("No user with " + email + " found");
  }
}
