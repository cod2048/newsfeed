package com.hanghae.module_newsfeed.dto.request;

import com.hanghae.module_newsfeed.entity.Newsfeed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewsfeedCreateRequest {
    private Long userId;
    private Newsfeed.ActivityType type;
    private Long targetId;
}
