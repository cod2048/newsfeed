package com.hanghae.module_activity.like.dto.likeRequest;

import com.hanghae.module_activity.like.entity.Likes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequest {
    private Long userId;
    private Likes.LikeType likeType;
}
