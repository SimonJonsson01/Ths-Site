package ths_site.backend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ths_site.backend.model.database.Admin;

public interface AdminRepository extends JpaRepository<Admin, UUID>{

  Optional<Admin> findByEmail(String email);

  boolean existsByEmail(String email);
  
}
