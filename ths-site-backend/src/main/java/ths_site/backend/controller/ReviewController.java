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
import ths_site.backend.model.database.Review;
import ths_site.backend.model.dto.ReviewDto;
import ths_site.backend.model.dto.ReviewSenderDto;
import ths_site.backend.service.CustomerService;
import ths_site.backend.service.ReviewService;

@EnableMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping("/review")
public class ReviewController {

  private final CustomerService customerService;
  private final ReviewService reviewService;

  public ReviewController(CustomerService customerService, ReviewService reviewService) {
    this.customerService = customerService;
    this.reviewService = reviewService;
  }

  /*
   * This function gets all reviews and returns a List<ReviewDto>
   * - AS OF NOW, OK!
   */
  @GetMapping(produces = "application/json")
  // public List<Review> getAll() {
  public List<ReviewDto> getAll() {
    // return this.reviewService.getAll();
    return this.reviewService.getAll().stream().map(Review::toDto).toList();
  }

  /*
   * This function gets a Review by id.
   * - AS OF NOW, OK!
   */
  @GetMapping(value = "/searchId", produces = "application/json")
  public ResponseEntity<ReviewDto> getById(@RequestParam UUID id) {
    Optional<Review> opReview = this.reviewService.getById(id);
    if (opReview.isPresent()) {
      ReviewDto dto = opReview.get().toDto();
      return ResponseEntity.ok(dto);
    }
    return ResponseEntity.notFound().build();
  }

  /*
   * This function gets a Review by customer id.
   * - AS OF NOW, OK!
   */
  @GetMapping(value = "/searchCustomerId", produces = "application/json")
  public ResponseEntity<ReviewDto> getByCustomerId(@RequestParam UUID id) {
    Optional<Review> opReview = this.reviewService.getByCustomerId(id);
    if (opReview.isPresent()) {
      ReviewDto dto = opReview.get().toDto();
      return ResponseEntity.ok(dto);
    }
    return ResponseEntity.ok().build();
  }

  /*
   * This function saves a new Review to the database.
   * - DEPRECATED
   */
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  @PostMapping(value = "/createNew", produces = "application/json")
  public ResponseEntity<ReviewDto> saveNew(@RequestBody Review review) {
    // Leta i reviewRepo om en review med customerId redan existerar
    Optional<Review> opReview = this.reviewService.getByCustomerId(review.getCustomer().getId());

    System.out.println(opReview);

    if (opReview.isPresent()) {
      return ResponseEntity.badRequest().build();
    }

    this.reviewService.save(review);

    return ResponseEntity.ok(review.toDto());
  }

  /*
   * This function saves a new Review to the database.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  @PostMapping(value = "/create", produces = "application/json")
  public ResponseEntity<ReviewDto> save(@RequestBody ReviewSenderDto review) {
    // Leta i reviewRepo om en review med customerId redan existerar
    Optional<Customer> opCustomer = this.customerService.getById(review.getCustomerId());

    if (opCustomer.isPresent()) {
      Customer customer = opCustomer.get();

      Optional<Review> opReview = this.reviewService.getByCustomerId(customer.getId());
      if (opReview.isPresent()) {
        return ResponseEntity.badRequest().build();
      }
      Review newReview = new Review(review, customer);

      this.reviewService.save(newReview);

      return ResponseEntity.ok(newReview.toDto());
    }
    return ResponseEntity.badRequest().build();
  }

  /*
   * This function updates a Review with data from ReviewDto.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  @PutMapping(value = "/update", produces = "application/json")
  public ResponseEntity<ReviewDto> put(@RequestParam UUID id, @RequestBody ReviewDto reviewDto) {
    Optional<Review> opReview = this.reviewService.getById(id);

    if (opReview.isPresent()) {
      Review review = opReview.get();
      Review updatedReview = this.reviewService.updateReview(review, reviewDto);
      this.reviewService.save(updatedReview);

      return ResponseEntity.ok(updatedReview.toDto());
    }

    return ResponseEntity.notFound().build();
  }

  /*
   * This function deletes a Review based on it's id.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  @DeleteMapping(value = "/delete", produces = "application/json")
  public ResponseEntity<UUID> delete(@RequestParam UUID id) {
    Optional<Review> opReview = this.reviewService.getById(id);

    if (opReview.isPresent()) {
      this.reviewService.deleteById(id);

      return ResponseEntity.ok(id);
    }

    return ResponseEntity.notFound().build();
  }

  /*
   * This function deletes a Review based on it's Customer's id.
   * - AS OF NOW, OK!
   */
  @PreAuthorize("hasAnyRole('CUSTOMER')")
  @DeleteMapping(value = "/deleteByCustomerId", produces = "application/json")
  public ResponseEntity<UUID> deleteByCustomerId(@RequestParam UUID id) {
    Optional<Customer> opCustomer = this.customerService.getById(id);

    if (opCustomer.isPresent()) {

      Optional<Review> opReview = this.reviewService.getByCustomerId(id);

      if (opReview.isPresent()) {
        Review review = opReview.get();

        this.reviewService.deleteById(review.getId());

        return ResponseEntity.ok(review.getId());
      }

      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.notFound().build();
  }

}
