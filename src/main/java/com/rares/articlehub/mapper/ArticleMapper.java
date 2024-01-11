package com.rares.articlehub.mapper;

import com.rares.articlehub.dto.ArticleHeaderResponse;
import com.rares.articlehub.dto.ArticleResponse;
import com.rares.articlehub.model.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {
    public ArticleHeaderResponse convertArticleToHeaderResponse(Article article) {
        return new ArticleHeaderResponse(
                article.getId(),
                article.getTitle(),
                article.getUser().getUsername()
        );
    }

    public ArticleResponse convertArticleToResponse(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getUser().getId()
        );
    }
}
