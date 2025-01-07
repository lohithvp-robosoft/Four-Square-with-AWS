package com.Robosoft.foursquare.services.Impl;

import com.Robosoft.foursquare.dto.request.user.ReviewRequest;
import com.Robosoft.foursquare.dto.request.user.UserLoginRequest;
import com.Robosoft.foursquare.dto.request.user.UserDetailRequest;
import com.Robosoft.foursquare.dto.ResponseDTO;
import com.Robosoft.foursquare.dto.response.user.UserDetailResponse;
import com.Robosoft.foursquare.dto.response.user.UserLoginResponse;
import com.Robosoft.foursquare.dto.response.user.UserRegisterResponse;
import com.Robosoft.foursquare.exception.EmailAlreadyExistsException;
import com.Robosoft.foursquare.exception.InvalidCredentialsException;
import com.Robosoft.foursquare.exception.NotFoundException;
import com.Robosoft.foursquare.jwt.JwtUtils;
import com.Robosoft.foursquare.modal.Hotel;
import com.Robosoft.foursquare.modal.Review;
import com.Robosoft.foursquare.modal.User;
import com.Robosoft.foursquare.repository.HotelRepository;
import com.Robosoft.foursquare.repository.ReviewRepository;
import com.Robosoft.foursquare.repository.UserRepository;
import com.Robosoft.foursquare.services.UserServices;
import com.Robosoft.foursquare.utils.EntityUpdaterUtil;
import com.Robosoft.foursquare.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EntityUpdaterUtil entityUpdaterUtil;

    @Value("${success.user.registration.message}")
    private String userRegistrationMessage;

    @Value("${error.hotel.notFound.message}")
    private String hotelNotFoundMessage;

    @Value("${error.user.notFound.message}")
    private String userNotFoundMessage;

    @Value("${success.user.review.added.message}")
    private String reviewAddedMessage;

    @Value("${success.user.updated.message}")
    private String userUpdatedMessage;

    @Value("${success.user.delete.message}")
    private String userDeletedMessage;

    @Override
    public ResponseEntity<ResponseDTO<UserRegisterResponse>> registerUser(UserDetailRequest userDetailRequest) {

        if (userRepository.existsByEmail(userDetailRequest.getEmail())) throw new EmailAlreadyExistsException();

        User newUser = new User(userDetailRequest, passwordEncoder.encode(userDetailRequest.getPassword()));
        userRepository.save(newUser);
        return responseUtil.successResponse(new UserRegisterResponse(newUser), userRegistrationMessage);
    }

    @Override
    public ResponseEntity<ResponseDTO<UserLoginResponse>> loginUser(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
            throw new InvalidCredentialsException();

        String token = jwtUtils.generateTokenFromUserDetails(user, user.getId().toString());

        return responseUtil.successResponse(new UserLoginResponse(user, token));
    }

    @Override
    public ResponseEntity<ResponseDTO<Void>> addReview(HttpServletRequest request, Long hotelId, ReviewRequest reviewRequest) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);
        User user = userRepository.findById(userId).get();
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException(hotelNotFoundMessage));

        Review review = new Review(reviewRequest, user, hotel);
        reviewRepository.save(review);

        return responseUtil.successResponse(reviewAddedMessage);
    }

    @Override
    public ResponseEntity<ResponseDTO<UserDetailResponse>> getUserDetail(HttpServletRequest request) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userNotFoundMessage));
        UserDetailResponse userDetailResponse = new UserDetailResponse(user);

        return responseUtil.successResponse(userDetailResponse);
    }

    @Override
    public ResponseEntity<ResponseDTO<UserDetailResponse>> updateUserDetail(HttpServletRequest request, UserDetailRequest updatedUser) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);
        User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException(userNotFoundMessage));
        User modifiedUser = entityUpdaterUtil.updateUser(updatedUser,user);
        userRepository.save(modifiedUser);

        return responseUtil.successResponse(new UserDetailResponse(modifiedUser), userUpdatedMessage);
    }

    @Override
    public ResponseEntity<ResponseDTO<UserDetailResponse>> deleteUser(HttpServletRequest request) {
        Long userId = jwtUtils.getUserIdFromRequestHeader(request);
        User user = userRepository.findById(userId).orElseThrow(()->new NotFoundException(userNotFoundMessage));
        UserDetailResponse userDetailResponse = new UserDetailResponse(user);
        userRepository.delete(user);

        return responseUtil.successResponse(userDetailResponse,userDeletedMessage);
    }


}
