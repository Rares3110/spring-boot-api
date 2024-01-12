package com.rares.articlehub.controller;

import com.rares.articlehub.dto.ArticleHeaderResponse;
import com.rares.articlehub.dto.ArticleResponse;
import com.rares.articlehub.dto.CategoryResponse;
import com.rares.articlehub.mapper.ArticleMapper;
import com.rares.articlehub.mapper.CategoryMapper;
import com.rares.articlehub.model.Article;
import com.rares.articlehub.model.Category;
import com.rares.articlehub.service.ArticleService;
import com.rares.articlehub.service.CategoryService;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleMapper articleMapper;
    private final CategoryMapper categoryMapper;

    public ArticleController(
            ArticleService articleService,
            ArticleMapper articleMapper,
            CategoryMapper categoryMapper) {
        this.articleService = articleService;
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable int id) {
        Optional<Article> articleOptional = articleService.getArticleById(id);
        return articleOptional
                .map(article -> ResponseEntity.ok().body(articleMapper.convertArticleToResponse(article)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/accepted-categories")
    public ResponseEntity<List<CategoryResponse>> getArticleCategories(@PathVariable int id) {
        Optional<Article> articleOptional = articleService.getArticleById(id);

        return articleOptional
                .map(article -> ResponseEntity.ok().body(articleService.getCategoriesForArticle(id)
                        .stream()
                        .map(categoryMapper::convertCategoryToResponse)
                        .collect(Collectors.toList())
                ))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/headers")
    public ResponseEntity<List<ArticleHeaderResponse>> getArticleHeaders() {
        return ResponseEntity.ok().body(
                articleService.getAllArticles().stream()
                        .map(articleMapper::convertArticleToHeaderResponse)
                        .collect(Collectors.toList())
        );
    }
}
