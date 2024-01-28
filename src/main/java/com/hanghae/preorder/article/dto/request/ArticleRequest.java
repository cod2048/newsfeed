package com.hanghae.preorder.article.dto.request;

import com.hanghae.preorder.user.entity.User;
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
