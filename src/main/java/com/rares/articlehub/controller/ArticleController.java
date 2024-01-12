package com.rares.articlehub.controller;

import com.rares.articlehub.dto.ArticleHeaderResponse;
import com.rares.articlehub.dto.ArticleRequest;
import com.rares.articlehub.dto.ArticleResponse;
import com.rares.articlehub.dto.CategoryResponse;
import com.rares.articlehub.mapper.ArticleMapper;
import com.rares.articlehub.mapper.CategoryMapper;
import com.rares.articlehub.model.Article;
import com.rares.articlehub.model.User;
import com.rares.articlehub.service.ArticleService;
import com.rares.articlehub.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    private final ArticleMapper articleMapper;
    private final CategoryMapper categoryMapper;

    public ArticleController(
            ArticleService articleService,
            UserService userService,
            ArticleMapper articleMapper,
            CategoryMapper categoryMapper) {
        this.articleService = articleService;
        this.userService = userService;
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable int id) {
        Optional<Article> articleOptional = articleService.findArticleById(id);
        return articleOptional
                .map(article -> ResponseEntity.ok().body(articleMapper.convertArticleToResponse(article)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/accepted-categories")
    public ResponseEntity<List<CategoryResponse>> getArticleCategories(@PathVariable int id) {
        Optional<Article> articleOptional = articleService.findArticleById(id);

        return articleOptional
                .map(article -> ResponseEntity.ok().body(articleService.getCategoriesForArticle(id)
                        .stream()
                        .map(categoryMapper::convertCategoryToResponse)
                        .collect(Collectors.toList())
                ))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/headers-list/")
    public ResponseEntity<List<ArticleHeaderResponse>> getArticleHeaderPage(@RequestParam int page,
                                                                            @RequestParam int size) {
        return ResponseEntity.ok().body(
                articleService
                        .findPage(PageRequest.of(page, size))
                        .getContent()
                        .stream()
                        .map(articleMapper::convertArticleToHeaderResponse)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/new")
    public ResponseEntity<ArticleResponse> saveArticle(@RequestBody ArticleRequest articleRequest) {
        Optional<User> userOptional = userService.findUserById(articleRequest.getUserId());

        return userOptional
                .map(user -> ResponseEntity.ok().body(articleMapper.convertArticleToResponse(
                        articleService.saveArticle(
                                articleMapper.convertRequestToArticle(articleRequest, user)
                        ))))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable int id,
                                                         @RequestParam(required = false) String title,
                                                         @RequestParam(required = false) String content) {
        Optional<Article> articleOptional = articleService.findArticleById(id);

        return articleOptional.map(article -> ResponseEntity.ok().body(
                        articleMapper.convertArticleToResponse(
                                articleService.updateArticle(id, title, content)
                        )))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteArticle(@PathVariable int id) {
        Optional<Article> articleOptional = articleService.findArticleById(id);
        if(articleOptional.isEmpty())
            return ResponseEntity.notFound().build();

        articleService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }
}
