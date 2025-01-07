package com.Robosoft.foursquare.dto.request.hotel;

import com.Robosoft.foursquare.modal.Category;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class HotelRequest {

    @NotBlank(message = "Hotel Name cannot be blank")
    private String name;

    @NotBlank(message = "Hotel Address cannot be blank")
    private String address;

    @NotBlank(message = "City cannot be blank")
    private String city;

    private String hours;

    private List<Category> categories;

    public HotelRequest(String name, String address, String city, String hours, List<Category> categories) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.hours = hours;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
