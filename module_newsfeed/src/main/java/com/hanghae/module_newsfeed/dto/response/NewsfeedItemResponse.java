package com.hanghae.module_newsfeed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class NewsfeedItemResponse {
    private Long userId;
    private String activityType; // 활동 유형 ("COMMENT", "LIKE", "FOLLOW", "ARTICLE")
    private Long targetId;
    private String details; // 활동 상세 내용
}
