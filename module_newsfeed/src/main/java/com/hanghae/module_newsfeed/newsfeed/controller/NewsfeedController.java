package com.hanghae.module_newsfeed.newsfeed.controller;

import com.hanghae.module_newsfeed.newsfeed.dto.response.NewsfeedItemResponse;
import com.hanghae.module_newsfeed.newsfeed.service.NewsfeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsfeedController {
    private final NewsfeedService newsfeedService;

    public NewsfeedController(NewsfeedService newsfeedService) {
        this.newsfeedService = newsfeedService;
    }

    @GetMapping("/api/newsfeed/{userId}")
    public ResponseEntity<List<NewsfeedItemResponse>> getUserNewsfeed(@PathVariable Long userId){
        List<NewsfeedItemResponse> newsfeedItems = newsfeedService.getUserNewsfeed(userId);
        return ResponseEntity.ok(newsfeedItems);
    }
}
