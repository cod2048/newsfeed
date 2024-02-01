package com.hanghae.preorder.like.service;

import com.hanghae.preorder.activity.entity.Activity;
import com.hanghae.preorder.activity.repository.ActivityRepository;
import com.hanghae.preorder.article.entity.Article;
import com.hanghae.preorder.article.repository.ArticleRepository;
import com.hanghae.preorder.comment.repository.CommentRepository;
import com.hanghae.preorder.like.dto.likeRequest.LikeRequest;
import com.hanghae.preorder.like.entity.ArticleLike;
import com.hanghae.preorder.like.repository.ArticleLikeRepository;
import com.hanghae.preorder.user.entity.User;
import com.hanghae.preorder.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final ActivityRepository activityRepository;

    public ArticleLikeService(UserRepository userRepository, ArticleRepository articleRepository, ArticleLikeRepository articleLikeRepository, ActivityRepository activityRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.articleLikeRepository = articleLikeRepository;
        this.activityRepository = activityRepository;
    }

    public void create(Long id, LikeRequest likeRequest) {
        User user = userRepository.findById(likeRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("can't find user"));

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("can't find article"));

        ArticleLike newLike = new ArticleLike(
                user,
                article
        );

        articleLikeRepository.save(newLike);

        // 좋아요에 대한 Activity 생성 및 저장
        Activity likeActivity = new Activity(user, Activity.ActivityType.ARTICLE_LIKE, article.getId());
        activityRepository.save(likeActivity);
    }
}
