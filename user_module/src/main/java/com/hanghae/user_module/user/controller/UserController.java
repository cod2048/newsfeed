package com.hanghae.preorder.user.controller;

import com.hanghae.preorder.security.UserDetailsImpl;
import com.hanghae.preorder.user.dto.request.LoginRequest;
import com.hanghae.preorder.user.dto.request.UserRequest;
import com.hanghae.preorder.user.dto.request.VerificationRequest;
import com.hanghae.preorder.user.dto.response.LoginResponse;
import com.hanghae.preorder.user.dto.response.UserResponse;
import com.hanghae.preorder.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/users")
    public ResponseEntity<UserResponse> signUp(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.create(userRequest));
    }

    @PostMapping("/api/verification")
    public ResponseEntity<Void> verifyEmail(@RequestBody VerificationRequest verificationRequest){
        log.info("이메일 인증 시작");
        String email = verificationRequest.getEmail();
        userService.verifyEmail(email);
        log.info("이메일 인증 끝");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        log.info("로그인 컨트롤러 진입");
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @RequestBody UserRequest userRequest,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long userId = userDetails.getId();
        return ResponseEntity.ok(userService.update(id, userId, userRequest));
    }

}
