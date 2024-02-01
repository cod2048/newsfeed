package com.hanghae.preorder.article.controller;

import com.hanghae.preorder.article.dto.request.ArticleRequest;
import com.hanghae.preorder.article.dto.response.ArticleResponse;
import com.hanghae.preorder.article.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/api/articles")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody ArticleRequest articleRequest){
        return ResponseEntity.ok(articleService.create(articleRequest));
    }
}
