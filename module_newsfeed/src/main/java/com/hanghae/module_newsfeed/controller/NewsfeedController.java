package com.hanghae.module_newsfeed.controller;

import com.hanghae.module_newsfeed.service.NewsfeedService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsfeedController {
    private final NewsfeedService newsfeedService;

    public NewsfeedController(NewsfeedService newsfeedService) {
        this.newsfeedService = newsfeedService;
    }

//    @GetMapping("/api/newsfeed/{userId}")
//    public ResponseEntity<List<NewsfeedItemResponse>> getUserNewsfeed(@PathVariable Long userId){
//        List<NewsfeedItemResponse> newsfeedItems = newsfeedService.getUserNewsfeed(userId);
//        return ResponseEntity.ok(newsfeedItems);
//    }
}
