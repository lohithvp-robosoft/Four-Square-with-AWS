package com.Robosoft.foursquare.services.Impl;

import com.Robosoft.foursquare.dto.request.hotel.HotelRequest;
import com.Robosoft.foursquare.dto.response.hotel.HotelResponse;
import com.Robosoft.foursquare.dto.ResponseDTO;
import com.Robosoft.foursquare.exception.NotFoundException;
import com.Robosoft.foursquare.modal.Hotel;
import com.Robosoft.foursquare.repository.HotelRepository;
import com.Robosoft.foursquare.services.HotelServices;
import com.Robosoft.foursquare.utils.EntityUpdaterUtil;
import com.Robosoft.foursquare.utils.FileStorageUtil;
import com.Robosoft.foursquare.utils.ResponseUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelServices {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private EntityUpdaterUtil entityUpdaterUtil;

    @Autowired
    private FileStorageUtil fileStorageUtil;

    @Value("${success.hotel.added.message}")
    private String hotelAddedMessage;

    @Value("${success.hotel.deleted.message}")
    private String hotelDeletedMessage;

    @Value("${error.hotel.notFound.message}")
    private String hotelNotFoundMessage;

    @Value("${success.hotel.updated.message}")
    private String hotelUpdatedMessage;

    @Override
    public ResponseEntity<ResponseDTO<Void>> addHotel(HotelRequest hotelRequest, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return responseUtil.errorResponse("No file uploaded.");
        }
        Hotel hotel = new Hotel(hotelRequest);
        hotelRepository.save(hotel);
        String imageUrl = fileStorageUtil.storeFile(file, hotel.getId());
        hotel.setImageUrl(imageUrl);
        hotelRepository.save(hotel);

        return responseUtil.successResponse(hotelAddedMessage);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<HotelResponse>>> getHotelListBySearch(String place) {
        List<Hotel> hotelList = hotelRepository.findByCityContainingIgnoreCase(place).get();

        if (hotelList.isEmpty()) throw new NotFoundException(hotelNotFoundMessage);

        List<HotelResponse> hotelResponseList = hotelList.stream().map(HotelResponse::new).toList();

        return responseUtil.successResponse(hotelResponseList);
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseDTO<HotelResponse>> getHotelById(Long id) throws IOException {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new NotFoundException(hotelNotFoundMessage));
        return responseUtil.successResponse(new HotelResponse(hotel));
    }

    @Override
    public ResponseEntity<ResponseDTO<HotelResponse>> deleteHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new NotFoundException(hotelNotFoundMessage));
        HotelResponse hotelResponse = new HotelResponse(hotel);
        hotelRepository.delete(hotel);

        return responseUtil.successResponse(hotelResponse, hotelDeletedMessage);
    }

    @Override
    public ResponseEntity<ResponseDTO<HotelResponse>> updateHotelById(Long hotelId, HotelRequest updatedHotel) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException(hotelNotFoundMessage));
        Hotel updateHotel = entityUpdaterUtil.updateHotel(updatedHotel, hotel);

        hotelRepository.save(updateHotel);

        return responseUtil.successResponse(new HotelResponse(updateHotel), hotelUpdatedMessage);
    }

    @Override
    public ResponseEntity<ResponseDTO<List<HotelResponse>>> getAllHotels() {
        List<Hotel> hotelList = hotelRepository.findAll();
        if (hotelList.isEmpty()) throw new NotFoundException(hotelNotFoundMessage);
        List<HotelResponse> hotelResponsesList = hotelList.stream().map(HotelResponse::new).toList();

        return responseUtil.successResponse(hotelResponsesList);
    }


}
