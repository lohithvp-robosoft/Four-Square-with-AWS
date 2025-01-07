package com.Robosoft.foursquare.utils;

import com.Robosoft.foursquare.dto.request.hotel.HotelRequest;
import com.Robosoft.foursquare.dto.request.user.UserDetailRequest;
import com.Robosoft.foursquare.modal.Hotel;
import com.Robosoft.foursquare.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EntityUpdaterUtil {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Hotel updateHotel(HotelRequest updatedHotel, Hotel existingHotel){
        if(updatedHotel.getName() != null) existingHotel.setName(updatedHotel.getName());
        if(updatedHotel.getAddress() != null) existingHotel.setAddress(updatedHotel.getAddress());
        if(updatedHotel.getCategories() != null) existingHotel.setCategories(updatedHotel.getCategories());
        if(updatedHotel.getCity() != null) existingHotel.setCity(updatedHotel.getCity());
        if(updatedHotel.getHours() != null) existingHotel.setHours(updatedHotel.getHours());
        return existingHotel;
    }

    public User updateUser(UserDetailRequest updatedUser, User existingUser){
        if(updatedUser.getEmail() != null) existingUser.setEmail(updatedUser.getEmail());
        if(updatedUser.getUsername() != null) existingUser.setUsername(updatedUser.getUsername());
        if(updatedUser.getPassword() != null){
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        return existingUser;
    }
}
