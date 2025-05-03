package ths_site.backend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ths_site.backend.model.database.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID>{

  List<Job> findAllByCustomerId(UUID id);


  void deleteAllByCustomerId(UUID id);
  
}