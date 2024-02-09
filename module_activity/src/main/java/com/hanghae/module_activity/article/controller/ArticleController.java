package com.hanghae.module_activity.article.controller;

import com.hanghae.module_activity.article.dto.request.CreateArticleRequest;
import com.hanghae.module_activity.article.dto.request.UpdateArticleRequest;
import com.hanghae.module_activity.article.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<?> createArticle(@RequestBody CreateArticleRequest createArticleRequest) {
        articleService.create(createArticleRequest);
        return ResponseEntity.ok().body("create article success");
    }

    @PostMapping("/api/articles/{articleId}")
    public ResponseEntity<?> updateArticle(@PathVariable Long articleId, @RequestBody UpdateArticleRequest updateArticleRequest) {
        articleService.update(articleId, updateArticleRequest);
        return ResponseEntity.ok().body("update article success");
    }
}
