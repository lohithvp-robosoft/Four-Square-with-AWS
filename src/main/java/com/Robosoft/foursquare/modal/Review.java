package com.Robosoft.foursquare.modal;

import com.Robosoft.foursquare.dto.request.user.ReviewRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@Table
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;


    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonIgnore
    private Hotel hotel;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String review;

    private int rating;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Review(ReviewRequest reviewRequest, User user, Hotel hotel) {
        this.user = user;
        this.hotel = hotel;
        this.review = reviewRequest.getReview();
        this.rating = reviewRequest.getRating();
    }

    public Review() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + (user != null ? user.getId() : null) +
                ", hotel=" + (hotel != null ? hotel.getId() : null) +
                ", review='" + review + '\'' +
                ", rating=" + rating +
                ", createdAt=" + createdAt +
                '}';
    }

}
