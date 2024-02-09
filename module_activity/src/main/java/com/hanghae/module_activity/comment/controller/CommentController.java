package com.hanghae.module_activity.comment.controller;

import com.hanghae.module_activity.comment.dto.request.CreateCommentRequest;
import com.hanghae.module_activity.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    private  final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/comments")
    public ResponseEntity<?> createComment(@RequestBody CreateCommentRequest createCommentRequest){
        commentService.create(createCommentRequest);
        return ResponseEntity.ok().body("create comment success");
    }

}
