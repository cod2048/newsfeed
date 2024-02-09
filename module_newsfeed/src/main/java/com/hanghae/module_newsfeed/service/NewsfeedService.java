package com.hanghae.module_newsfeed.service;

import com.hanghae.module_newsfeed.client.ActivityClient;
import com.hanghae.module_newsfeed.client.UserClient;
import com.hanghae.module_newsfeed.dto.request.NewsfeedCreateRequest;
import com.hanghae.module_newsfeed.dto.response.NewsfeedItemResponse;
import com.hanghae.module_newsfeed.entity.Newsfeed;
import com.hanghae.module_newsfeed.repository.NewsfeedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    public List<NewsfeedItemResponse> getUserNewsfeed(Long userId) {
        List<Long> followingIds = activityClient.findFollowingIds(userId);
        log.info("뉴스피드 조회 진입" + followingIds);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        return newsfeedRepository.findByUserIdIn(followingIds, sort).stream()
                .map(newsfeed -> {
                    log.info("유저 아이디 조회 결과" + newsfeed);
                    String details = generateActivityDetails(newsfeed);
                    return new NewsfeedItemResponse(
                            newsfeed.getUserId(),
                            newsfeed.getType().toString(),
                            newsfeed.getTargetId(),
                            details
                    );
                }).collect(Collectors.toList());
    }

    private String generateActivityDetails(Newsfeed newsfeed) {
        return switch (newsfeed.getType()) {
            case ARTICLE -> {
                Long userId = newsfeed.getUserId();
                String userName = userClient.findUserName(userId);
                yield userName + "님이 새 글을 작성했습니다.";
            }
            case COMMENT -> {
                Long userId = newsfeed.getUserId();
                String userName = userClient.findUserName(userId);
                Long targetId = newsfeed.getTargetId();

                yield userName + "님이 " + targetId + "글에 댓글을 남겼습니다.";
            }
            case ARTICLE_LIKE -> {
                Long userId = newsfeed.getUserId();
                String userName = userClient.findUserName(userId);
                Long targetId = newsfeed.getTargetId();

                yield userName + "님이 " + targetId + " 글에 좋아요를 눌렀습니다.";
            }
            case COMMENT_LIKE -> {
                Long userId = newsfeed.getUserId();
                String userName = userClient.findUserName(userId);
                Long targetId = newsfeed.getTargetId();

                yield userName + "님이 " + targetId + " 댓글에 좋아요를 눌렀습니다.";
            }
            case FOLLOW -> {
                Long userId = newsfeed.getUserId();
                String userName = userClient.findUserName(userId);
                Long targetId = newsfeed.getTargetId();
                String followingName = userClient.findUserName(targetId);

                yield userName + "님이 " + followingName + "님을 팔로우했습니다.";
            }
            default -> "알 수 없는 활동";
        };
    }
}
