package com.hanghae.module_newsfeed.user.entity;

import com.hanghae.module_newsfeed.user.dto.request.UserRequest;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "profile_image", nullable = false)
    private String profileImage;
    @Column(name = "greeting", nullable = false)
    private String greeting;
    public User() {
    }

    public User(
            String email,
            String password,
            String name,
            String profileImage,
            String greeting
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileImage = profileImage;
        this.greeting = greeting;
    }


    public User update(UserRequest userRequest) {
        this.password = userRequest.getPassword();
        this.name = userRequest.getName();
        this.profileImage = userRequest.getProfileImage();
        this.greeting = userRequest.getGreeting();

        return this;
    }
}