package com.hanghae.module_user.user.entity;

import com.hanghae.module_user.user.dto.request.CreateUserRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_num")
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

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public User() {
    }

    @Builder
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

    public static User create(CreateUserRequest createUserRequest, String encodedPassword) {

        return User.builder()
                .email(createUserRequest.getEmail())
                .password(encodedPassword)
                .name(createUserRequest.getName())
                .profileImage(createUserRequest.getProfileImage())
                .greeting(createUserRequest.getGreeting())
                .build();
    }

//    public User updateProfile(UpdateUserRequest updateUserRequest) {
//        this.name = updateUserRequest.getName();
//        this.profileImage = updateUserRequest.getProfileImage();
//        this.greeting = updateUserRequest.getGreeting();
//
//        return this;
//    }

//    public User updatePassword(UpdatePasswordRequest updatePasswordRequest) {
//
//    }
}