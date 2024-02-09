package com.hanghae.module_activity.like.service;

import com.hanghae.module_activity.article.entity.Article;
import com.hanghae.module_activity.article.repository.ArticleRepository;
import com.hanghae.module_activity.client.UserClient;
import com.hanghae.module_activity.comment.entity.Comment;
import com.hanghae.module_activity.comment.repository.CommentRepository;
import com.hanghae.module_activity.like.dto.likeRequest.LikeRequest;
import com.hanghae.module_activity.like.entity.Likes;
import com.hanghae.module_activity.like.repository.LikeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    private final UserClient userClient;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public LikeService(UserClient userClient, ArticleRepository articleRepository, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.userClient = userClient;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }

    public void create(Long id, LikeRequest likeRequest) {
        Long userId = likeRequest.getUserId();

        if (!userClient.checkUserExists(userId)) {
            throw new IllegalArgumentException("user not exists");
        }

        Likes.LikeType likeType = likeRequest.getLikeType();

        switch (likeType) {
            case ARTICLE -> {
                Article article = articleRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("article not found"));
                Likes newlike = new Likes(userId, Likes.LikeType.ARTICLE, article.getId());
                likeRepository.save(newlike);
            }
            case COMMENT -> {
                Comment comment = commentRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("comment not found"));
                Likes newlike = new Likes(userId, Likes.LikeType.COMMENT, comment.getId());
                likeRepository.save(newlike);
            }
            default -> throw new IllegalStateException("Unexpected value: " + likeType);
        }

    }
}
