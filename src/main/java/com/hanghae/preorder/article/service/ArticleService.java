package com.hanghae.preorder.article.service;

import com.hanghae.preorder.article.dto.request.ArticleRequest;
import com.hanghae.preorder.article.dto.response.ArticleResponse;
import com.hanghae.preorder.article.entity.Article;
import com.hanghae.preorder.article.repository.ArticleRepository;
import com.hanghae.preorder.user.entity.User;
import com.hanghae.preorder.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }


    public ArticleResponse create(ArticleRequest articleRequest) {
        //0. 받아온 userId로 user찾기
        User user = userRepository.findById(articleRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("can't find user"));

        //1. articleRequest로 entity 생성
        Article newArticle = new Article(
                user,
                articleRequest.getTitle(),
                articleRequest.getContent()
        );

        //2. 생성된 entity db에 저장
        Article createdArticle = articleRepository.save(newArticle);

        //3. entity를 articleResponse로 변환해서 반환
        return new ArticleResponse(
                createdArticle.getId()
        );
    }
}
