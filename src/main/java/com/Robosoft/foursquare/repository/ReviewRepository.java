package com.Robosoft.foursquare.repository;

import com.Robosoft.foursquare.modal.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
