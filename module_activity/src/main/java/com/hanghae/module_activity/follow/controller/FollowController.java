package com.hanghae.module_activity.follow.controller;

import com.hanghae.module_activity.follow.dto.request.FollowRequest;
import com.hanghae.module_activity.follow.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService){
        this.followService = followService;
    }

    @PostMapping("/api/follows")
    public ResponseEntity<?> createFollow(@RequestBody FollowRequest followRequest){
        log.info("api요청 전송 진입");
        followService.create(followRequest);
        log.info("service에서 create 완료");
        return ResponseEntity.ok().body("follow success");
    }
}
