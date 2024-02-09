package com.hanghae.module_activity.like.controller;

import com.hanghae.module_activity.like.dto.likeRequest.LikeRequest;
import com.hanghae.module_activity.like.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/api/likes/{id}")
    public ResponseEntity<?> createLike(@RequestBody LikeRequest likeRequest, @PathVariable Long id) {
        likeService.create(id, likeRequest);
        return ResponseEntity.ok().body("like success");
    }

}
