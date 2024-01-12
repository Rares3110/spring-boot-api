package com.rares.articlehub.service;

import com.rares.articlehub.model.Article;
import com.rares.articlehub.model.Category;
import com.rares.articlehub.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> findArticleById(int id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public List<Category> getCategoriesForArticle(int id) {
        Article article = articleRepository.findById(id).orElse(null);

        if (article != null)
            return new ArrayList<>(article.getCategories());

        return Collections.emptyList();
    }

    public Page<Article> findPage(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Transactional
    public Article updateArticle(int id, String title, String content) {
        Optional<Article> articleOptional = articleRepository.findById(id);

        if(articleOptional.isEmpty())
            throw new IllegalStateException("article not found");

        articleOptional.get().setTitle(title);
        articleOptional.get().setContent(content);

        return articleOptional.get();
    }

    public void deleteArticle(int id) {
        articleRepository.deleteById(id);
    }
}
