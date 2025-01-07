package com.Robosoft.foursquare.services;

import com.Robosoft.foursquare.dto.request.hotel.HotelRequest;
import com.Robosoft.foursquare.dto.response.hotel.HotelResponse;
import com.Robosoft.foursquare.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface HotelServices {

    ResponseEntity<ResponseDTO<Void>> addHotel(HotelRequest hotelRequest,  MultipartFile file) throws IOException;

    ResponseEntity<ResponseDTO<List<HotelResponse>>> getHotelListBySearch(String city);

    ResponseEntity<ResponseDTO<HotelResponse>> getHotelById(Long id) throws IOException;

    ResponseEntity<ResponseDTO<HotelResponse>> deleteHotelById(Long id);

    ResponseEntity<ResponseDTO<HotelResponse>> updateHotelById(Long hotelId, HotelRequest updatedHotel);

    ResponseEntity<ResponseDTO<List<HotelResponse>>> getAllHotels();
}
