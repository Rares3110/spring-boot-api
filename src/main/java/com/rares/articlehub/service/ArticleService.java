package com.rares.articlehub.service;

import com.rares.articlehub.model.Article;
import com.rares.articlehub.model.Category;
import com.rares.articlehub.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(int id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public List<Category> getCategoriesForArticle(int id) {
        Article article = articleRepository.findById(id).orElse(null);

        if (article != null)
            return new ArrayList<>(article.getCategories());

        return Collections.emptyList();
    }
}
