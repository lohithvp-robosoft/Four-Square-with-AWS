package com.Robosoft.foursquare.dto.response.user;

import com.Robosoft.foursquare.modal.Review;

import java.time.LocalDateTime;

public class UserReviewResponse {
    private Long id;
    private String review;
    private int rating;
    private String hotelName;
    private LocalDateTime createdAt;

    public UserReviewResponse(Review review) {
        this.id = review.getId();
        this.review = review.getReview();
        this.rating = review.getRating();
        this.hotelName = review.getHotel().getName();
        this.createdAt = review.getCreatedAt();
    }

    public UserReviewResponse(Long id, String review, int rating, String hotelName) {
        this.id = id;
        this.review = review;
        this.rating = rating;
        this.hotelName = hotelName;
    }

    public Long getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    public String getHotelName() {
        return hotelName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
