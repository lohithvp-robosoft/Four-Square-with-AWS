package com.Robosoft.foursquare.dto.response.user;

import com.Robosoft.foursquare.modal.Role;
import com.Robosoft.foursquare.modal.User;

import java.util.ArrayList;
import java.util.List;

public class UserLoginResponse {

    private Long id;

    private String email;

    private String userName;

    private List<Role> roles = new ArrayList<>();

    private String token;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getToken() {
        return token;
    }

    public UserLoginResponse(User user, String token) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.userName = user.getUsername();
        this.roles = user.getRoles();
        this.token = token;
    }

}
