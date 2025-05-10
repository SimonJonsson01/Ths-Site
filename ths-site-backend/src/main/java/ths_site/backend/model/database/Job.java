package ths_site.backend.model.database;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import ths_site.backend.model.dto.JobDto;

@Entity
@Table(name = "job")
public class Job {

    @Id
    private UUID id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "created_at")
    @OrderBy("created_at ASC")
    private LocalDateTime createdAt;

    private boolean completed;

    public Job() {
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    public Job(Customer customer, String title, String content) {
        this();
        this.id = UUID.randomUUID();
        this.customer = customer;
        this.title = title;
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCurrentTime() {
        this.createdAt = LocalDateTime.now();
    }

    public boolean getCompleted() {
        return completed;
    }

    // - "Togglar" mellan true och false när denna metoden kallas.
    public void toggleCompleted() {
        this.completed = !this.getCompleted();
    }

    public JobDto toDto() {
        return new JobDto(title, content, createdAt, customer.toDto(), completed);
    }
}
