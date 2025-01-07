package com.Robosoft.foursquare.services;

import com.Robosoft.foursquare.dto.request.user.ReviewRequest;
import com.Robosoft.foursquare.dto.request.user.UserLoginRequest;
import com.Robosoft.foursquare.dto.request.user.UserDetailRequest;
import com.Robosoft.foursquare.dto.ResponseDTO;
import com.Robosoft.foursquare.dto.response.user.UserDetailResponse;
import com.Robosoft.foursquare.dto.response.user.UserLoginResponse;
import com.Robosoft.foursquare.dto.response.user.UserRegisterResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserServices {

    ResponseEntity<ResponseDTO<UserRegisterResponse>> registerUser(UserDetailRequest userDetailRequest);

    ResponseEntity<ResponseDTO<UserLoginResponse>> loginUser(UserLoginRequest userLoginRequest);

    ResponseEntity<ResponseDTO<Void>> addReview(HttpServletRequest request, Long hotelId, ReviewRequest reviewRequest);

    ResponseEntity<ResponseDTO<UserDetailResponse>> getUserDetail(HttpServletRequest request);

    ResponseEntity<ResponseDTO<UserDetailResponse>> updateUserDetail(HttpServletRequest request, UserDetailRequest userDetailRequest);

    ResponseEntity<ResponseDTO<UserDetailResponse>> deleteUser(HttpServletRequest request);
}
