package ths_site.backend.model.database;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import ths_site.backend.model.dto.ReviewDto;
import ths_site.backend.model.dto.ReviewSenderDto;

@Entity
@Table(name = "review")
public class Review {

    @Id
    private UUID id;

    private String title;

    private int score;

    private String content;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "created_at")
    @OrderBy("created_at ASC")
    private LocalDateTime createdAt;

    public Review() {
        this.createdAt = LocalDateTime.now();
    }

    public Review(ReviewSenderDto reviewDto, Customer customer) {
        this();
        this.id = UUID.randomUUID();
        this.title = reviewDto.getTitle();
        this.score = reviewDto.getScore();
        this.content = reviewDto.getContent();
        this.customer = customer;
    }

    public Review(UUID id, String title, int score, String content, Customer customer) {
        this.id = id;
        this.title = title;
        this.score = score;
        this.content = content;
        this.customer = customer;
        this.createdAt = LocalDateTime.now();
    }

    public Review(UUID id, String title, int score, String content, Customer customer, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.score = score;
        this.content = content;
        this.customer = customer;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getScore() {
        return score;
    }

    public String getContent() {
        return content;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ReviewDto toDto() {
        return new ReviewDto(title, content, score, customer.toDto());
    }

    public void setCurrentTime() {
      this.createdAt = LocalDateTime.now();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
