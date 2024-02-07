package com.hanghae.module_user.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String email;
    private String name;
}
