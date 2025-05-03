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

import ths_site.backend.model.database.Review;
import ths_site.backend.model.dto.ReviewDto;
import ths_site.backend.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {

  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
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
    return ResponseEntity.notFound().build();
  }

  /*
   * This function saves a new Review to the database.
   * - AS OF NOW, OK!
   */
  @PostMapping(value = "/create", produces = "application/json")
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
   * This function updates a Review with data from ReviewDto.
   * - AS OF NOW, OK!
   */
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
  @DeleteMapping(value = "/delete", produces = "application/json")
  public ResponseEntity<UUID> delete(@RequestParam UUID id) {
    Optional<Review> opReview = this.reviewService.getById(id);

    if (opReview.isPresent()) {
      this.reviewService.deleteById(id);

      return ResponseEntity.ok(id);
    }

    return ResponseEntity.notFound().build();
  }

}
