package com.Robosoft.foursquare.dto.request.user;

import jakarta.validation.constraints.NotNull;

public class ReviewRequest {

    @NotNull(message = "Review content cannot be null")
    private String review;

    @NotNull(message = "Rating cannot be null")
    private int rating;

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ReviewRequest(String review, int rating) {
        this.review = review;
        this.rating = rating;
    }
}
