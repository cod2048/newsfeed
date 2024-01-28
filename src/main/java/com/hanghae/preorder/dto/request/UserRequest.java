package com.hanghae.preorder.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
}
