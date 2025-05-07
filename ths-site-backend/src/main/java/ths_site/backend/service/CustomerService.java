package ths_site.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

  public CustomerService(CustomerRepository customerRepository

      , JobRepository jobRepository, ReviewRepository reviewRepository) {
    this.customerRepository = customerRepository;

    this.jobRepository = jobRepository;
    this.reviewRepository = reviewRepository;

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
  
  // - Bytar ut gammal data hos Customer med ny data från CustomerData
  public Customer updateCustomer(Customer customer, UserData customerData) {
    customer.setFirstName(customer.getFirstName().toLowerCase().equals(customerData.getFirstName().toLowerCase())
    ? customer.getFirstName()
    : customerData.getFirstName());
    customer.setLastName(customer.getLastName().toLowerCase().equals(customerData.getLastName().toLowerCase())
    ? customer.getLastName()
    : customerData.getLastName());
    customer.setEmail(customer.getEmail().toLowerCase().equals(customerData.getEmail().toLowerCase())
    ? customer.getEmail()
    : customerData.getEmail());
    customer.setPassword(customer.getPassword().toLowerCase().equals(customerData.getPassword().toLowerCase())
    ? customer.getPassword()
    : customerData.getPassword());
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
