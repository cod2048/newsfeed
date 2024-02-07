package com.hanghae.module_activity.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentRequest {
    private Long userId;
    private Long articleId;
    private String content;
}
