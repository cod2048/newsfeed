package com.hanghae.module_newsfeed.controller;

import com.hanghae.module_newsfeed.dto.request.NewsfeedCreateRequest;
import com.hanghae.module_newsfeed.service.NewsfeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/internal/newsfeeds")
public class InternalNewsfeedController {
    private final NewsfeedService newsfeedService;

    public InternalNewsfeedController(NewsfeedService newsfeedService) {
        this.newsfeedService = newsfeedService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody NewsfeedCreateRequest newsfeedCreateRequest
    ) {
        newsfeedService.create(newsfeedCreateRequest);
        return ResponseEntity.ok().body("create newsfeed success");
    }
}
