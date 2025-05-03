package ths_site.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ths_site.backend.model.database.Job;
import ths_site.backend.model.dto.JobDto;
import ths_site.backend.repository.JobRepository;

@Service
public class JobService {

  private final JobRepository jobRepository;

  public JobService(JobRepository jobRepository) {
    this.jobRepository = jobRepository;
  }

  // - Hämtar alla jobb.
  public List<Job> getAll() {
    return this.jobRepository.findAll();
  }

  // - Hämtar alla jobb baserad på customer_id.
  public List<Job> getAllByCustomerId(UUID id) {
    return this.jobRepository.findAllByCustomerId(id);
  }

  // - Hämtar ett jobb efter jobbets id.
  public Optional<Job> getById(UUID id) {
    return this.jobRepository.findById(id);
  }

  // - Spara ett jobb
  public Job save(Job job) {
    return this.jobRepository.save(job);
  }

  // - Ändra innehållet av jobbet (FÖR ADMIN)
  public Job updateJob(Job job, JobDto jobDto) {
    job.setTitle(job.getTitle().toLowerCase().equals(jobDto.getTitle().toLowerCase())
        ? job.getTitle()
        : jobDto.getTitle());
    job.setContent(job.getContent().toLowerCase().equals(jobDto.getContent().toLowerCase())
        ? job.getContent()
        : jobDto.getContent());
    return job;
  }

  // - Radera ett jobb efter id
  public void deleteById(UUID id) {
    this.jobRepository.deleteById(id);
  }

  // - Radera alla jobb efter customer_id
  public void deleteAllByCustomerId(UUID id) {
    this.jobRepository.deleteAll(jobRepository.findAllByCustomerId(id));
  }

}
