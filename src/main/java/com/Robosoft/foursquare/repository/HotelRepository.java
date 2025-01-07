package com.Robosoft.foursquare.repository;

import com.Robosoft.foursquare.modal.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<List<Hotel>> findByCityContainingIgnoreCase(String city);
}
