package com.hanghae.module_activity.article.service;

import com.hanghae.module_activity.article.dto.request.CreateArticleRequest;
import com.hanghae.module_activity.article.dto.request.UpdateArticleRequest;
import com.hanghae.module_activity.article.entity.Article;
import com.hanghae.module_activity.article.repository.ArticleRepository;
import com.hanghae.module_activity.client.UserClient;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
//    private final ActivityRepository activityRepository;
    private final UserClient userClient;

    public ArticleService(ArticleRepository articleRepository, UserClient userClient) {
        this.articleRepository = articleRepository;
//        this.activityRepository = activityRepository;
        this.userClient = userClient;
    }


    public void create(CreateArticleRequest createArticleRequest) {
        //0. 받아온 userId로 user찾기
        Long userId = createArticleRequest.getUserId();

        if (!userClient.checkUserExists(userId)) {
            throw new IllegalArgumentException("user not exists");
        }


        //1. articleRequest로 entity 생성
        Article newArticle = new Article(
                userId,
                createArticleRequest.getTitle(),
                createArticleRequest.getContent()
        );

        //2. 생성된 entity db에 저장
        articleRepository.save(newArticle);

        //3. Article 작성에 대한 Activity 생성 및 저장
//        Activity activity = new Activity(user, Activity.ActivityType.ARTICLE, createdArticle.getId());
//        activityRepository.save(activity);
    }

    public void update(Long articleId, UpdateArticleRequest updateArticleRequest) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not exists"));

        article.updateArticle(updateArticleRequest);
    }
}
