package com.hanghae.module_activity.comment.service;

import com.hanghae.module_activity.article.entity.Article;
import com.hanghae.module_activity.article.repository.ArticleRepository;
import com.hanghae.module_activity.client.NewsfeedClient;
import com.hanghae.module_activity.client.NewsfeedClientRequest;
import com.hanghae.module_activity.client.UserClient;
import com.hanghae.module_activity.comment.dto.request.CreateCommentRequest;
import com.hanghae.module_activity.comment.entity.Comment;
import com.hanghae.module_activity.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserClient userClient;
    private final NewsfeedClient newsfeedClient;

    public CommentService(ArticleRepository articleRepository, CommentRepository commentRepository, UserClient userClient, NewsfeedClient newsfeedClient) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.userClient = userClient;
        this.newsfeedClient = newsfeedClient;
    }

    public void create(CreateCommentRequest createCommentRequest) {
        Long userId = createCommentRequest.getUserId();
        String type = "COMMENT";

        Article article = articleRepository.findById(createCommentRequest.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("can't find article"));

        if (!userClient.checkUserExists(userId)) {
            throw new IllegalArgumentException("user not exists");
        }

        Comment newComment = Comment.builder()
                .userId(userId)
                .article(article)
                .content(createCommentRequest.getContent())
                .build();

        commentRepository.save(newComment);

        NewsfeedClientRequest newsfeedClientRequest = new NewsfeedClientRequest(
                userId,
                type,
                article.getId()
        );

        newsfeedClient.create(newsfeedClientRequest);

    }
}
