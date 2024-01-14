package com.rares.articlehub.controller;

import com.rares.articlehub.dto.*;
import com.rares.articlehub.mapper.ArticleMapper;
import com.rares.articlehub.mapper.CategoryMapper;
import com.rares.articlehub.mapper.CommentMapper;
import com.rares.articlehub.mapper.ExternalResourceMapper;
import com.rares.articlehub.model.Article;
import com.rares.articlehub.model.User;
import com.rares.articlehub.service.ArticleService;
import com.rares.articlehub.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    private final UserService userService;

    private final ArticleMapper articleMapper;

    private final CategoryMapper categoryMapper;

    private final CommentMapper commentMapper;

    private final ExternalResourceMapper externalResourceMapper;

    public ArticleController(
            ArticleService articleService,
            UserService userService,
            ArticleMapper articleMapper,
            CategoryMapper categoryMapper,
            CommentMapper commentMapper,
            ExternalResourceMapper externalResourceMapper) {
        this.articleService = articleService;
        this.userService = userService;
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
        this.commentMapper = commentMapper;
        this.externalResourceMapper = externalResourceMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable int id) {
        Optional<Article> articleOptional = articleService.findArticleById(id);
        return articleOptional
                .map(article -> ResponseEntity.ok().body(articleMapper.convertArticleToResponse(article)))
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

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponse>> getArticleComments(@PathVariable int id) {
        Optional<Article> articleOptional = articleService.findArticleById(id);

        return articleOptional
                .map(article -> ResponseEntity.ok().body(articleService.getCommentsForArticle(id)
                        .stream()
                        .map(commentMapper::convertCommentToResponse)
                        .collect(Collectors.toList())
                ))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/external-resources")
    public ResponseEntity<List<ExternalResourceResponse>> getExternalResources(@PathVariable int id) {
        Optional<Article> articleOptional = articleService.findArticleById(id);

        return articleOptional
                .map(article -> ResponseEntity.ok().body(articleService.getExternalResourcesForArticle(id)
                        .stream()
                        .map(externalResource -> {
                            try {
                                return externalResourceMapper.convertExternalResourceToResponse(externalResource);
                            }
                            catch (MalformedURLException e) {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
                ))
                .orElseGet(() -> ResponseEntity.notFound().build());
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
                                                         @RequestBody(required = false) String content) {
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
