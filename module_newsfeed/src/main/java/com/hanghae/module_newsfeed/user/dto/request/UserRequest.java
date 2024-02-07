package com.hanghae.module_newsfeed.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UserRequest {
    private String email;
    private String password;
    private String name;
    private String profileImage;
    private String greeting;
    private String verificationCode;

    public void updatePassword(String newPassword, BCryptPasswordEncoder encoder) {
        this.password = encoder.encode(newPassword);
    }
}
