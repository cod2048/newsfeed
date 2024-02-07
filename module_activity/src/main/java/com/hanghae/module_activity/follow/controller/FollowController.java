package com.hanghae.module_activity.follow.controller;

import com.hanghae.module_activity.follow.dto.request.FollowRequest;
import com.hanghae.module_activity.follow.dto.response.FollowResponse;
import com.hanghae.module_activity.follow.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService){
        this.followService = followService;
    }

    @PostMapping("/api/follows")
    public ResponseEntity<FollowResponse> createFollow(@RequestBody FollowRequest followRequest){
        return ResponseEntity.ok(followService.create(followRequest));
    }
}
