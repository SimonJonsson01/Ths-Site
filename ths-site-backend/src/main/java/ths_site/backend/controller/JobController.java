package ths_site.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ths_site.backend.model.database.Job;
import ths_site.backend.model.dto.JobDto;
import ths_site.backend.service.JobService;

@RestController
@RequestMapping("/job")
public class JobController {

  private final JobService jobService;

  public JobController(JobService jobService) {
    this.jobService = jobService;
  }

  /*
   * This function get all Jobs as JobDto in a List.
   * - AS OF NOW, OK!
   */
  @GetMapping(produces = "application/json")
  public ResponseEntity<List<JobDto>> getAll() {
    List<JobDto> list = this.jobService.getAll().stream().map(Job::toDto).toList();
    return ResponseEntity.ok(list);

  }

  /*
   * This function finds a Job based on id.
   */
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
  @GetMapping(value = "/searchByCustomerId", produces = "application/json")
  public ResponseEntity<List<JobDto>> getAllByCustomerId(@RequestParam UUID id) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    List<JobDto> list = this.jobService.getAllByCustomerId(id).stream().map(Job::toDto).toList();
    return ResponseEntity.ok(list);
  }

  /*
   * This function saves a new Job to the database.
   * - AS OF NOW, OK!
   */
  @PostMapping(value = "/create", produces = "application/json")
  public ResponseEntity<JobDto> saveNew(@RequestBody Job job) {
    if (job.getCreatedAt() == null) {
      job.setCurrentTime();
    }

    this.jobService.save(job);

    return ResponseEntity.ok(job.toDto());
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
