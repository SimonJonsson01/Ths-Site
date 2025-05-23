package ths_site.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ths_site.backend.model.database.Customer;
import ths_site.backend.model.database.Job;
import ths_site.backend.model.dto.JobDto;
import ths_site.backend.model.dto.JobSenderDto;
import ths_site.backend.service.CustomerService;
import ths_site.backend.service.JobService;

@EnableMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping("/job")
public class JobController {

  private final CustomerService customerService;
  private final JobService jobService;

  public JobController(CustomerService customerService, JobService jobService) {
    this.customerService = customerService;
    this.jobService = jobService;
  }

  /*
   * This function get all Jobs as JobDto in a List.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping(produces = "application/json")
  public ResponseEntity<List<JobDto>> getAllasDto() {
    List<JobDto> list = this.jobService.getAll().stream().map(Job::toDto).toList();
    return ResponseEntity.ok(list);

  }

  /*
   * This function get all Jobs in a List.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping(value = "/getAll", produces = "application/json")
  public ResponseEntity<List<Job>> getAll() {
    List<Job> list = this.jobService.getAll();
    return ResponseEntity.ok(list);

  }

  /*
   * This function finds a Job based on id.
   */
  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping(value = "/searchId", produces = "application/json")
  public ResponseEntity<JobDto> getById(@RequestParam UUID id) {
    Optional<Job> opJop = this.jobService.getById(id);

    if (opJop.isPresent()) {
      Job job = opJop.get();
      return ResponseEntity.ok(job.toDto());
    }
    return ResponseEntity.notFound().build();
  }

  /*
   * This function finds all Jobs belonging to a Customer's id.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  @GetMapping(value = "/searchByCustomerId", produces = "application/json")
  public ResponseEntity<List<Job>> getAllByCustomerId(@RequestParam UUID id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    List<Job> list = this.jobService.getAllByCustomerId(id);
    return ResponseEntity.ok(list);
  }

  /*
   * This function saves a new Job to the database.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  @PostMapping(value = "/create", produces = "application/json")
  public ResponseEntity<JobDto> saveNew(@RequestBody JobSenderDto jobDto) {
    Optional<Customer> opCustomer = this.customerService.getById(jobDto.getCustomerId());

    if (opCustomer.isPresent()) {

      Customer customer = opCustomer.get();
      
      Job job = new Job(customer, jobDto.getTitle(), jobDto.getContent());
      
      this.jobService.save(job);
      
      return ResponseEntity.ok(job.toDto());
    }
    
    return ResponseEntity.notFound().build();
  }

  /*
   * This function updates a Job, based on id, with the data from JobDto.
   * - AS OF NOW, OK!
   */
  @PutMapping(value = "/update", produces = "application/json")
  public ResponseEntity<JobDto> put(@RequestParam UUID id, @RequestBody JobDto jobDto) {
    Optional<Job> opJob = this.jobService.getById(id);

    if (opJob.isPresent()) {
      Job job = opJob.get();
      job = this.jobService.updateJob(job, jobDto);
      this.jobService.save(job);
      return ResponseEntity.ok(job.toDto());
    }
    return ResponseEntity.notFound().build();
  }

  /*
   * This function updates a Job, based on id, with it's current completion.
   * Can also go from completed to incompleted.
   * - AS OF NOW, OK!
   */
  @PutMapping(value = "/complete", produces = "application/json")
  public ResponseEntity<JobDto> put(@RequestParam UUID id) {
    Optional<Job> opJob = this.jobService.getById(id);

    if (opJob.isPresent()) {
      Job job = opJob.get();
      job.toggleCompleted();
      this.jobService.save(job);
      return ResponseEntity.ok(job.toDto());
    }
    return ResponseEntity.notFound().build();
  }

  /*
   * This function deletes a Job based on id.
   * - AS OF NOW, OK!
   */
  @DeleteMapping(value = "/delete", produces = "application/json")
  public ResponseEntity<UUID> delete(@RequestParam UUID id) {
    Optional<Job> opJob = this.jobService.getById(id);

    if (opJob.isPresent()) {
      this.jobService.deleteById(id);
      return ResponseEntity.ok(id);
    }
    return ResponseEntity.notFound().build();
  }

  /*
   * This function deletes all Jobs based on Customer's id.
   * - AS OF NOW, OK!
   */
  @DeleteMapping(value = "/deleteAllByCustomerId", produces = "application/json")
  public ResponseEntity<UUID> deleteAllByCustomerId(@RequestParam UUID id) {
    this.jobService.deleteAllByCustomerId(id);
    return ResponseEntity.ok(id);
  }

}
