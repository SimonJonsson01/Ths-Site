package ths_site.backend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ths_site.backend.model.database.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID>{

  void deleteByCustomerId(UUID id);

  Optional<Review> findByCustomerId(UUID id);
  
}
