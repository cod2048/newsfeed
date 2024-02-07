package com.hanghae.module_user.comment.service;

import com.hanghae.module_user.activity.entity.Activity;
import com.hanghae.module_user.activity.repository.ActivityRepository;
import com.hanghae.module_user.article.entity.Article;
import com.hanghae.module_user.article.repository.ArticleRepository;
import com.hanghae.module_user.comment.dto.request.CommentRequest;
import com.hanghae.module_user.comment.dto.response.CommentResponse;
import com.hanghae.module_user.comment.entity.Comment;
import com.hanghae.module_user.comment.repository.CommentRepository;
import com.hanghae.module_user.user.entity.User;
import com.hanghae.module_user.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final ActivityRepository activityRepository;

    public CommentService(UserRepository userRepository, ArticleRepository articleRepository, CommentRepository commentRepository, ActivityRepository activityRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.activityRepository = activityRepository;
    }

    public CommentResponse create(CommentRequest commentRequest) {
        User user = userRepository.findById(commentRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("can't find user"));

        Article article = articleRepository.findById(commentRequest.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("can't find article"));

        Comment newComment = new Comment(
                user,
                article,
                commentRequest.getContent()
        );

        Comment createdComment = commentRepository.save(newComment);

        Activity commentActivity = new Activity(user, Activity.ActivityType.COMMENT, createdComment.getId());
        activityRepository.save(commentActivity);

        return new CommentResponse(
                createdComment.getId()
        );
    }
}
