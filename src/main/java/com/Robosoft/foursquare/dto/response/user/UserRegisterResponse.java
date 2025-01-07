package com.Robosoft.foursquare.dto.response.user;

import com.Robosoft.foursquare.modal.Role;
import com.Robosoft.foursquare.modal.User;

import java.util.ArrayList;
import java.util.List;

public class UserRegisterResponse {

    private Long id;

    private String userName;

    private String email;

    private List<Role> roles = new ArrayList<>();

    public UserRegisterResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.userName = user.getUsername();
        this.roles = user.getRoles();
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
