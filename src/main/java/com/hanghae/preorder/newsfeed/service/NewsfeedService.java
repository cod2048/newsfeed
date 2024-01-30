package com.hanghae.preorder.newsfeed.service;

import com.hanghae.preorder.newsfeed.dto.response.NewsfeedItemResponse;
import com.hanghae.preorder.activity.entity.Activity;
import com.hanghae.preorder.activity.repository.ActivityRepository;
import com.hanghae.preorder.article.repository.ArticleRepository;
import com.hanghae.preorder.comment.repository.CommentRepository;
import com.hanghae.preorder.user.repository.UserRepository;
import com.hanghae.preorder.follow.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        return activityRepository.findByUserIdIn(followingIds).stream()
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
                yield activity.getUser().getName() + "님이 " + articleTitle + "에 댓글을 남겼습니다.";
                // 댓글이 달린 게시글의 제목을 포함
            }
            case LIKE ->
                // 좋아요가 눌린 대상(게시글 또는 댓글)의 정보를 포함
                // 이 부분은 해당 애플리케이션의 데이터 모델에 따라 구현 방법이 달라질 수 있습니다.
                    activity.getUser().getName() + "님이 좋아요를 눌렀습니다.";
            case FOLLOW -> {
                String followedUserName = userRepository.findById(activity.getTargetId())
                        .map(user -> user.getName())
                        .orElse("알 수 없는 사용자");
                yield activity.getUser().getName() + "님이 " + followedUserName + "님을 팔로우했습니다.";
                // 팔로우된 사용자의 이름을 포함
            }
            default -> "알 수 없는 활동";
        };
    }
}
