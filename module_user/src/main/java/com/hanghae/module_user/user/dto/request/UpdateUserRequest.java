package com.hanghae.module_user.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UpdateUserRequest {
    private String name;
    private String profileImage;
    private String greeting;

}
