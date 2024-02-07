package com.hanghae.module_activity.like.controller;

import com.hanghae.module_activity.like.dto.likeRequest.LikeRequest;
import com.hanghae.module_activity.like.service.ArticleLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;

    public ArticleLikeController(ArticleLikeService articleLikeService){
        this.articleLikeService = articleLikeService;
    }

    /**
     *
     * @param likeRequest, id
     * @return 좋아요 성공 여부(http response)
     */
    @PostMapping("/api/articles/{id}/like")
    public ResponseEntity<Void> createLike(@RequestBody LikeRequest likeRequest, @PathVariable Long id) {
        articleLikeService.create(id, likeRequest);
        return ResponseEntity.ok().build();
    }
}
