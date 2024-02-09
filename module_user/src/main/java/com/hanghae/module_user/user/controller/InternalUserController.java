package com.hanghae.module_user.user.controller;

import com.hanghae.module_user.user.service.InternalUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/users")
public class InternalUserController {
    private final InternalUserService userService;

    public InternalUserController(InternalUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkUserExists(
            @RequestParam(name = "userId") Long principalId
    ) {
        return ResponseEntity.ok().body(userService.checkUserExists(principalId));
    }

    @GetMapping("/find")
    public ResponseEntity<?> findUserName(
            @RequestParam(name = "userId") Long principalId
    ) {
        return ResponseEntity.ok().body(userService.findUserName(principalId));
    }
}
