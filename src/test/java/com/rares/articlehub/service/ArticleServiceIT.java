package com.rares.articlehub.service;

import com.rares.articlehub.model.*;
import com.rares.articlehub.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ArticleServiceIT {
    @InjectMocks
    private ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;

    private List<Article> mockArticles;

    @BeforeEach
    public void setUp() {
        mockArticles = Arrays.asList(
                new Article(1, " ", " ", null, List.of(new Category(), new Category()), null, null, null, null, null),
                new Article(2, " ", " ", null, null, null, null, null, null, null)
        );
        when(articleRepository.findAll()).thenReturn(mockArticles);
        when(articleRepository.findById(mockArticles.get(0).getId())).thenReturn(Optional.of(mockArticles.get(0)));
    }

    @Test
    public void getAllArticlesTest() {
        assertEquals(articleService.getAllArticles().size(), mockArticles.size());
    }

    @Test
    public void getArticleByIdTest() {
        Optional<Article> optArticleForSure = articleService.getArticleById(articleRepository.findAll().get(0).getId());
        assertTrue(optArticleForSure.isPresent());

        assertEquals(articleRepository.findAll().get(0), optArticleForSure.get());
    }

    @Test
    public void getCategoriesForArticleTest() {
        Article articleForSure = articleRepository.findAll().get(0);
        Optional <Article> optArticleForSure = articleService.getArticleById(articleForSure.getId());
        assertTrue(optArticleForSure.isPresent());
        assertEquals(articleForSure.getCategories().size(), articleService.getCategoriesForArticle(articleForSure.getId()).size());
    }
}
