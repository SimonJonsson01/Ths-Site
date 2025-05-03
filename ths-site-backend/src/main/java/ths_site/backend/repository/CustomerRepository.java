package ths_site.backend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ths_site.backend.model.database.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID>{

  Optional<Customer> findByEmail(String email);

  boolean existsByEmail(String email);
  
}