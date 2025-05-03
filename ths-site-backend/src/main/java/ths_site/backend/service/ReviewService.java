package ths_site.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ths_site.backend.model.database.Review;
import ths_site.backend.model.dto.ReviewDto;
import ths_site.backend.repository.ReviewRepository;

@Service
public class ReviewService {

  private final ReviewRepository reviewRepository;

  public ReviewService(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  // - Hämtar review efter id.
  public Optional<Review> getById(UUID id) {
    return this.reviewRepository.findById(id);
  }

  // - Hämtar review efter customer id.
  public Optional<Review> getByCustomerId(UUID id) {
    return this.reviewRepository.findByCustomerId(id);
  }

  // - Hämtar alla reviewer.
  public List<Review> getAll() {
    return this.reviewRepository.findAll();
  }

  // - Spara en review. Lägger till tidpunkt om det saknas.
  public Review save(Review review) {
    if (review.getCreatedAt() == null) {
      review.setCurrentTime();
    }
    return this.reviewRepository.save(review);
  }

  // - Ändra en review
  public Review updateReview(Review review, ReviewDto reviewDto) {
    review.setTitle(review.getTitle().toLowerCase().equals(reviewDto.getTitle().toLowerCase())
        ? review.getTitle()
        : reviewDto.getTitle());
    review.setScore(review.getScore() == reviewDto.getScore()
        ? review.getScore()
        : reviewDto.getScore());
    review.setContent(review.getContent().toLowerCase().equals(reviewDto.getContent().toLowerCase())
        ? review.getContent()
        : reviewDto.getContent());
    return review;
  }

  // - Ta bort en review efter id.
  public void deleteById(UUID id) {
    this.reviewRepository.deleteById(id);
  }

}
