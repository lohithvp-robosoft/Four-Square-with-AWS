package com.Robosoft.foursquare.modal;

import com.Robosoft.foursquare.dto.request.user.UserDetailRequest;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    private static final Log log = LogFactory.getLog(User.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    public User(UserDetailRequest userRegisterRequest, String encodedPassword){
        this.username = userRegisterRequest.getUsername();
        this.email = userRegisterRequest.getEmail();
        this.password = encodedPassword;
        if (userRegisterRequest.getRoles() == null || userRegisterRequest.getRoles().isEmpty()) {
            this.roles.add(Role.USER);
        } else {
            this.roles = userRegisterRequest.getRoles();
        }
    }

    public User(String email, String password, List<Role> roles, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
        if (roles == null ||roles.isEmpty()) {
            log.info("No roles provided, setting default role");
            this.roles.add(Role.USER);
        } else {
            this.roles = roles;
        }
    }
    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
