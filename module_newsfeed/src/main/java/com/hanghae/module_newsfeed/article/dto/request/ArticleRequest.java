package com.hanghae.module_newsfeed.article.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ArticleRequest {
    private Long userId;
    private String title;
    private String content;
}
