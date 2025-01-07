package com.Robosoft.foursquare.controller;

import com.Robosoft.foursquare.dto.request.user.ReviewRequest;
import com.Robosoft.foursquare.dto.request.user.UserLoginRequest;
import com.Robosoft.foursquare.dto.request.user.UserDetailRequest;
import com.Robosoft.foursquare.dto.ResponseDTO;
import com.Robosoft.foursquare.dto.response.user.UserDetailResponse;
import com.Robosoft.foursquare.dto.response.user.UserLoginResponse;
import com.Robosoft.foursquare.dto.response.user.UserRegisterResponse;
import com.Robosoft.foursquare.repository.HotelRepository;
import com.Robosoft.foursquare.repository.ReviewRepository;
import com.Robosoft.foursquare.repository.UserRepository;
import com.Robosoft.foursquare.services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserServices userServices;

    @GetMapping
    public ResponseEntity<ResponseDTO<UserDetailResponse>> getUserById(HttpServletRequest request) {
        return userServices.getUserDetail(request);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<UserDetailResponse>> updateUser(HttpServletRequest request, @RequestBody UserDetailRequest userDetailRequest){
        return userServices.updateUserDetail(request,userDetailRequest);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO<UserDetailResponse>> deleteUser(HttpServletRequest request){
        return userServices.deleteUser(request);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<UserRegisterResponse>> registerUser(@Valid @RequestBody UserDetailRequest request) {
        return userServices.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<UserLoginResponse>> loginUser(@Valid @RequestBody UserLoginRequest request) {
        return userServices.loginUser(request);
    }

    @PostMapping("/review/{hotelId}")
    public ResponseEntity<ResponseDTO<Void>> addReview(@RequestBody ReviewRequest reviewRequest, HttpServletRequest request, @PathVariable Long hotelId) {
        return userServices.addReview(request, hotelId, reviewRequest);
    }
}
