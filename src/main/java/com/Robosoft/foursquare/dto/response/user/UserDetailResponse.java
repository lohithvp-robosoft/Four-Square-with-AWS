package com.Robosoft.foursquare.dto.response.user;

import com.Robosoft.foursquare.modal.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailResponse extends UserRegisterResponse {

    private List<UserReviewResponse> reviews;

    private LocalDateTime createAt;

    public UserDetailResponse(User user) {
        super(user);
        this.createAt = user.getCreatedAt();
        this.reviews = user.getReviews().stream()
                .map(UserReviewResponse::new)
                .collect(Collectors.toList());
    }

    public List<UserReviewResponse> getReviews() {
        return reviews;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
}
