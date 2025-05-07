package ths_site.backend.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ths_site.backend.model.database.Admin;
import ths_site.backend.model.database.Customer;
import ths_site.backend.repository.AdminRepository;
import ths_site.backend.repository.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final AdminRepository adminRepository;
  private final CustomerRepository customerRepository;
  //private static final Logger LOGGER = LoggerFactory.getLogger("TEST-CustomUserDetailsService");

  public CustomUserDetailsService(AdminRepository adminRepository,
      CustomerRepository customerRepository) {
    this.adminRepository = adminRepository;
    this.customerRepository = customerRepository;
  }

  /*
   * The function says Username, but is loading the user by the email instead.
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    //LOGGER.info("----------------loadUserByUsername START-------------------------------");

    Optional<Customer> opCustomer = this.customerRepository.findByEmail(email);
    if (opCustomer.isPresent()) {
      //LOGGER.info("opCustomer.isPresent()");
      Customer user = opCustomer.get();
      Set<GrantedAuthority> authorities = new HashSet<>();
      authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
      //LOGGER.info("opCustomer.get()");
      return new org.springframework.security.core.userdetails.User(
          user.getEmail(), user.getPassword(), authorities);
    }

    Optional<Admin> opAdmin = this.adminRepository.findByEmail(email);
    if (opAdmin.isPresent()) {
      //LOGGER.info("opAdmin.isPresent()");
      Admin user = opAdmin.get();
      Set<GrantedAuthority> authorities = new HashSet<>();
      authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
      //LOGGER.info("opAdmin.get()");
      return new User(user.getEmail(), user.getPassword(),
          authorities);
    }
    //LOGGER.info("No user with " + email + " was not found");
    throw new UsernameNotFoundException("No user with " + email + " was not found");
  }
}
