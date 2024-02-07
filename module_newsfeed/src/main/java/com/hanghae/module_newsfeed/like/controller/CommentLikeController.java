package com.hanghae.module_newsfeed.like.controller;

import com.hanghae.module_newsfeed.like.dto.likeRequest.LikeRequest;
import com.hanghae.module_newsfeed.like.service.CommentLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    public CommentLikeController(CommentLikeService commentLikeService){
        this.commentLikeService = commentLikeService;
    }

    @PostMapping("/api/comments/{id}/like")
    public ResponseEntity<Void> createLike(@RequestBody LikeRequest likeRequest, @PathVariable Long id) {
        commentLikeService.create(id, likeRequest);
        return ResponseEntity.ok().build();
    }
}
