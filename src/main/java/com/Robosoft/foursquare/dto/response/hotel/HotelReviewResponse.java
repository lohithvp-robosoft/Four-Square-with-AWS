package com.Robosoft.foursquare.dto.response.hotel;

import com.Robosoft.foursquare.modal.Review;

import java.time.LocalDateTime;

public class HotelReviewResponse {
    private Long id;
    private String review;
    private int rating;
    private String reviewerName;
    private LocalDateTime createdAt;

    public HotelReviewResponse(Long id, String review, int rating, String reviewerName) {
        this.id = id;
        this.review = review;
        this.rating = rating;
        this.reviewerName = reviewerName;
    }

    public HotelReviewResponse(Review review) {
        this.id = review.getId();
        this.review = review.getReview();
        this.rating = review.getRating();
        this.reviewerName = review.getUser().getUsername();
        this.createdAt = review.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public int getRating() {
        return rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
