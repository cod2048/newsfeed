package com.hanghae.module_newsfeed.newsfeed.service;

import com.hanghae.module_newsfeed.newsfeed.dto.response.NewsfeedItemResponse;
import com.hanghae.module_newsfeed.activity.entity.Activity;
import com.hanghae.module_newsfeed.activity.repository.ActivityRepository;
import com.hanghae.module_newsfeed.article.repository.ArticleRepository;
import com.hanghae.module_newsfeed.comment.repository.CommentRepository;
import com.hanghae.module_newsfeed.user.repository.UserRepository;
import com.hanghae.module_newsfeed.follow.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsfeedService {
    private final ActivityRepository activityRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Autowired
    public NewsfeedService(ActivityRepository activityRepository,
                           ArticleRepository articleRepository,
                           CommentRepository commentRepository,
                           UserRepository userRepository,
                           FollowRepository followRepository) {
        this.activityRepository = activityRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    public List<NewsfeedItemResponse> getUserNewsfeed(Long userId) {
        List<Long> followingIds = followRepository.findByFollowerId(userId).stream()
                .map(follow -> follow.getFollowing().getId())
                .collect(Collectors.toList());

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        return activityRepository.findByUserIdIn(followingIds, sort).stream()
                .map(activity -> {
                    String details = generateActivityDetails(activity);
                    return new NewsfeedItemResponse(
                            activity.getId(),
                            activity.getUser().getName(),
                            activity.getType().toString(),
                            details
                    );
                }).collect(Collectors.toList());
    }

    private String generateActivityDetails(Activity activity) {
        return switch (activity.getType()) {
            case ARTICLE -> activity.getUser().getName() + "님이 새 글을 작성했습니다.";
            case COMMENT -> {
                String articleTitle = articleRepository.findById(activity.getTargetId())
                        .map(article -> article.getTitle())
                        .orElse("알 수 없는 게시글");
                yield activity.getUser().getName() + "님이 " + articleTitle + " 댓글을 남겼습니다.";
            }
            case ARTICLE_LIKE -> {
                String articleTitleForLike = articleRepository.findById(activity.getTargetId())
                        .map(article -> article.getTitle())
                        .orElse("알 수 없는 게시글");
                yield activity.getUser().getName() + "님이 " + articleTitleForLike + " 글에 좋아요를 눌렀습니다.";
            }
            case COMMENT_LIKE -> {
                String commentContentForLike = commentRepository.findById(activity.getTargetId())
                        .map(comment -> comment.getContent())
                        .orElse("알 수 없는 댓글");
                yield activity.getUser().getName() + "님이 " + commentContentForLike + " 댓글에 좋아요를 눌렀습니다.";
            }
            case FOLLOW -> {
                String followedUserName = userRepository.findById(activity.getTargetId())
                        .map(user -> user.getName())
                        .orElse("알 수 없는 사용자");
                yield activity.getUser().getName() + "님이 " + followedUserName + "님을 팔로우했습니다.";
            }
            default -> "알 수 없는 활동";
        };
    }
}
