package ths_site.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ths_site.backend.model.database.Customer;
import ths_site.backend.model.database.Review;
import ths_site.backend.model.dto.UserData;
import ths_site.backend.repository.CustomerRepository;
import ths_site.backend.repository.JobRepository;
import ths_site.backend.repository.ReviewRepository;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final JobRepository jobRepository;
  private final ReviewRepository reviewRepository;
  private final PasswordEncoder passwordEncoder;

  public CustomerService(CustomerRepository customerRepository, JobRepository jobRepository,
      ReviewRepository reviewRepository, PasswordEncoder passwordEncoder) {
    this.customerRepository = customerRepository;
    this.jobRepository = jobRepository;
    this.reviewRepository = reviewRepository;
    this.passwordEncoder = passwordEncoder;
  }

  // - Hittar all Customers
  public List<Customer> findAll() {
    return this.customerRepository.findAll();
  }

  // - Hittar en Customer via id
  public Optional<Customer> getById(UUID id) {
    return this.customerRepository.findById(id);
  }

  // - Hittar en Customer via email
  public Optional<Customer> getByEmail(String email) {
    return this.customerRepository.findByEmail(email);
  }

  // - Kollar om email redan existerar
  public boolean ifEmailExists(String email) {
    return this.customerRepository.existsByEmail(email);
  }

  // - Kollar om id redan existerar
  public boolean ifIdExists(UUID id) {
    return this.customerRepository.existsById(id);
  }

  // - Sparar Customer till databasen
  public Customer save(Customer customer) {
    return this.customerRepository.save(customer);
  }

  // - Sparar UserData (skapar nytt id) till databasen
  public Customer saveNew(UserData customerData) {
    Customer customer = new Customer(customerData);
    customer.setPassword(passwordEncoder.encode(customerData.getPassword()));
    return this.customerRepository.save(customer);
  }

  // - Bytar ut gammal data hos Customer med ny data från CustomerData
  public Customer updateCustomer(Customer customer, UserData userData) {
    customer.setFirstName(customer.getFirstName().toLowerCase().equals(userData.getFirstName().toLowerCase())
        ? customer.getFirstName()
        : userData.getFirstName());
    customer.setLastName(customer.getLastName().toLowerCase().equals(userData.getLastName().toLowerCase())
        ? customer.getLastName()
        : userData.getLastName());
    customer.setEmail(customer.getEmail().toLowerCase().equals(userData.getEmail().toLowerCase())
        ? customer.getEmail()
        : userData.getEmail());
    customer.setPassword(customer.getPassword().toLowerCase().equals(userData.getPassword().toLowerCase())
        ? customer.getPassword()
        : userData.getPassword());
    return customer;
  }

  // - Raderar en Customer via id, samt review och jobb som tillhör sagd Customer.
  public void deleteById(UUID id) {
    this.jobRepository.deleteAll(jobRepository.findAllByCustomerId(id));
    Optional<Review> opReview = this.reviewRepository.findByCustomerId(id);
    if (opReview.isPresent()) {
      this.reviewRepository.delete(opReview.get());
    }
    this.customerRepository.deleteById(id);
  }

}
