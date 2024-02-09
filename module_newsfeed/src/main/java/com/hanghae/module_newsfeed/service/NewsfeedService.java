package com.hanghae.module_newsfeed.service;

import com.hanghae.module_newsfeed.client.ActivityClient;
import com.hanghae.module_newsfeed.client.UserClient;
import com.hanghae.module_newsfeed.dto.request.NewsfeedCreateRequest;
import com.hanghae.module_newsfeed.entity.Newsfeed;
import com.hanghae.module_newsfeed.repository.NewsfeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsfeedService {
    private final NewsfeedRepository newsfeedRepository;
    private final UserClient userClient;
    private final ActivityClient activityClient;

    @Autowired
    public NewsfeedService(NewsfeedRepository newsfeedRepository,
                           UserClient userClient,
                           ActivityClient activityClient) {
        this.newsfeedRepository = newsfeedRepository;
        this.userClient = userClient;
        this.activityClient = activityClient;
    }

    public void create(NewsfeedCreateRequest newsfeedCreateRequest) {
        Newsfeed newsfeed = Newsfeed.builder()
                .userId(newsfeedCreateRequest.getUserId())
                .type(newsfeedCreateRequest.getType())
                .targetId(newsfeedCreateRequest.getTargetId())
                .build();

        newsfeedRepository.save(newsfeed);
    }

//    public List<NewsfeedItemResponse> getUserNewsfeed(Long userId) {
//        List<Long> followingIds = followRepository.findByFollowerId(userId).stream()
//                .map(follow -> follow.getFollowing().getId())
//                .collect(Collectors.toList());
//
//        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
//
//        return newsfeedRepository.findByUserIdIn(followingIds, sort).stream()
//                .map(newsfeed -> {
//                    String details = generateActivityDetails(newsfeed);
//                    return new NewsfeedItemResponse(
//                            newsfeed.getId(),
//                            newsfeed.getUser().getName(),
//                            newsfeed.getType().toString(),
//                            details
//                    );
//                }).collect(Collectors.toList());
//    }
//
//    private String generateActivityDetails(Newsfeed newsfeed) {
//        return switch (newsfeed.getType()) {
//            case ARTICLE -> newsfeed.getUser().getName() + "님이 새 글을 작성했습니다.";
//            case COMMENT -> {
//                String articleTitle = articleRepository.findById(newsfeed.getTargetId())
//                        .map(article -> article.getTitle())
//                        .orElse("알 수 없는 게시글");
//                yield newsfeed.getUser().getName() + "님이 " + articleTitle + " 댓글을 남겼습니다.";
//            }
//            case ARTICLE_LIKE -> {
//                String articleTitleForLike = articleRepository.findById(newsfeed.getTargetId())
//                        .map(article -> article.getTitle())
//                        .orElse("알 수 없는 게시글");
//                yield newsfeed.getUser().getName() + "님이 " + articleTitleForLike + " 글에 좋아요를 눌렀습니다.";
//            }
//            case COMMENT_LIKE -> {
//                String commentContentForLike = commentRepository.findById(newsfeed.getTargetId())
//                        .map(comment -> comment.getContent())
//                        .orElse("알 수 없는 댓글");
//                yield newsfeed.getUser().getName() + "님이 " + commentContentForLike + " 댓글에 좋아요를 눌렀습니다.";
//            }
//            case FOLLOW -> {
//                String followedUserName = userRepository.findById(newsfeed.getTargetId())
//                        .map(user -> user.getName())
//                        .orElse("알 수 없는 사용자");
//                yield newsfeed.getUser().getName() + "님이 " + followedUserName + "님을 팔로우했습니다.";
//            }
//            default -> "알 수 없는 활동";
//        };
//    }
}
