package com.hanghae.module_activity.follow.controller;

import com.hanghae.module_activity.follow.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/internal")
public class InternalFollowController {
    private final FollowService followService;

    public InternalFollowController(FollowService followService) {
        this.followService = followService;
    }

    // 내가 팔로우한 사용자 id 리스트
    @GetMapping("/follows")
    public ResponseEntity<List<Long>> findFollowing(@RequestParam(name = "userId") Long principalId) {
        return ResponseEntity.ok().body(followService.findByFollowingId(principalId));
    }

    // 나를 팔로우한 사용자 id 리스트
    @GetMapping("/followers")
    public ResponseEntity<List<Long>> findFollower(@RequestParam(name = "userId") Long principalId) {
        return ResponseEntity.ok().body(followService.findByFollowerId(principalId));
    }
}
