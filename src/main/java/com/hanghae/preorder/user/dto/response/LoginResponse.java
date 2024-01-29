package com.hanghae.preorder.user.dto.response;

import com.hanghae.preorder.security.jwt.TokenType;
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
