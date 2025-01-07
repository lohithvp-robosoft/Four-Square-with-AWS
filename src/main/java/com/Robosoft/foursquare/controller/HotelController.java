package com.Robosoft.foursquare.controller;

import com.Robosoft.foursquare.dto.request.hotel.HotelRequest;
import com.Robosoft.foursquare.dto.response.hotel.HotelResponse;
import com.Robosoft.foursquare.dto.ResponseDTO;
import com.Robosoft.foursquare.services.HotelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/hotel")
public class HotelController {

    @Autowired
    private HotelServices hotelServices;

    @GetMapping
    public ResponseEntity<ResponseDTO<List<HotelResponse>>> getAllHotel() {
        return hotelServices.getAllHotels();
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<Void>> addHotel(@ModelAttribute  HotelRequest hotelRequest, @RequestParam MultipartFile file) throws IOException {
        return hotelServices.addHotel(hotelRequest, file);
    }

    @DeleteMapping("/admin/{hotelId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<HotelResponse>> deleteHotel(@PathVariable Long hotelId) {
        return hotelServices.deleteHotelById(hotelId);
    }

    @PutMapping("/admin/{hotelId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<HotelResponse>> updateHotel(@RequestBody HotelRequest updatedHotel, @PathVariable Long hotelId) {
        return hotelServices.updateHotelById(hotelId, updatedHotel);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<ResponseDTO<List<HotelResponse>>> getHotelByCity(@PathVariable String city) {
        return hotelServices.getHotelListBySearch(city);
    }

    @GetMapping("/id/{hotelId}")
    public ResponseEntity<ResponseDTO<HotelResponse>> getHotelById(@PathVariable Long hotelId) throws IOException {
        return hotelServices.getHotelById(hotelId);
    }
}
