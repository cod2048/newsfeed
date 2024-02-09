package com.hanghae.module_activity.comment.service;

import com.hanghae.module_activity.article.entity.Article;
import com.hanghae.module_activity.article.repository.ArticleRepository;
import com.hanghae.module_activity.client.UserClient;
import com.hanghae.module_activity.comment.dto.request.CreateCommentRequest;
import com.hanghae.module_activity.comment.entity.Comment;
import com.hanghae.module_activity.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
//    private final ActivityRepository activityRepository;
    private final UserClient userClient;

    public CommentService(ArticleRepository articleRepository, CommentRepository commentRepository, UserClient userClient) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
//        this.activityRepository = activityRepository;
        this.userClient = userClient;
    }

    public void create(CreateCommentRequest createCommentRequest) {
        Long userId = createCommentRequest.getUserId();

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

//        Activity commentActivity = new Activity(user, Activity.ActivityType.COMMENT, createdComment.getId());
//        activityRepository.save(commentActivity);


    }
}
