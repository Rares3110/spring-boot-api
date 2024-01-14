package com.rares.articlehub.service;

import com.rares.articlehub.model.*;
import com.rares.articlehub.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ArticleServiceTest {
    @InjectMocks
    private ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;

    private List<Article> mockArticles;

    @BeforeEach
    public void setUp() {
        mockArticles = Arrays.asList(
                new Article(
                        1,
                        " ",
                        " ",
                        List.of(new ExternalResource(), new ExternalResource()),
                        List.of(new Category(), new Category()),
                        null,
                        null,
                        null,
                        null,
                        List.of(new Comment(), new Comment())
                ),
                new Article(
                        2,
                        " ",
                        " ",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );
        when(articleRepository.save(mockArticles.get(0))).thenReturn(mockArticles.get(0));
        when(articleRepository.save(mockArticles.get(1))).thenReturn(mockArticles.get(1));
        when(articleRepository.findAll()).thenReturn(mockArticles);
        when(articleRepository.findById(mockArticles.get(0).getId())).thenReturn(Optional.of(mockArticles.get(0)));
        when(articleRepository.findById(mockArticles.get(1).getId())).thenReturn(Optional.of(mockArticles.get(1)));
    }

    @Test
    public void getAllArticlesTest() {
        assertEquals(articleService.findAllArticles().size(), mockArticles.size());
    }

    @Test
    public void getArticleByIdTest() {
        Optional<Article> optArticleForSure = articleService.findArticleById(articleRepository.findAll().get(0).getId());
        assertTrue(optArticleForSure.isPresent());

        assertEquals(articleRepository.findAll().get(0), optArticleForSure.get());
    }

    @Test
    void findPageTest() {
        Pageable pageable = PageRequest.of(0, 10);
        when(articleRepository.findAll(pageable)).thenReturn(new PageImpl<>(mockArticles));

        Page<Article> result = articleService.findPage(pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void getCategoriesForArticleTest() {
        Article articleForSure = articleRepository.findAll().get(0);
        Optional <Article> optArticleForSure = articleService.findArticleById(articleForSure.getId());
        assertTrue(optArticleForSure.isPresent());
        assertEquals(articleForSure.getCategories().size(), articleService.getCategoriesForArticle(articleForSure.getId()).size());
    }

    @Test
    public void getCommentsForArticleTest() {
        Article articleForSure = articleRepository.findAll().get(0);
        Optional <Article> optArticleForSure = articleService.findArticleById(articleForSure.getId());
        assertTrue(optArticleForSure.isPresent());
        assertEquals(articleForSure.getComments().size(), articleService.getCommentsForArticle(articleForSure.getId()).size());
    }

    @Test
    public void getExternalResourcesForArticleTest() {
        Article articleForSure = articleRepository.findAll().get(0);
        Optional <Article> optArticleForSure = articleService.findArticleById(articleForSure.getId());
        assertTrue(optArticleForSure.isPresent());
        assertEquals(articleForSure.getExternalResources().size(), articleService.getExternalResourcesForArticle(articleForSure.getId()).size());
    }

    @Test
    void saveArticleTest() {
        Article article = new Article();
        article.setTitle("Test Title");
        article.setContent("Test Content");

        when(articleRepository.save(article)).thenReturn(article);

        Article savedArticle = articleService.saveArticle(article);

        assertNotNull(savedArticle);
        assertEquals(article.getTitle(), savedArticle.getTitle());
        assertEquals(article.getContent(), savedArticle.getContent());
    }

    @Test
    void updateArticleTest() {
        int articleId = 1;
        String newTitle = "Updated Title";
        String newContent = "Updated Content";

        Article updatedArticle = articleService.updateArticle(articleId, newTitle, newContent);

        assertNotNull(updatedArticle);
        assertEquals(articleId, updatedArticle.getId());
        assertEquals(newTitle, updatedArticle.getTitle());
        assertEquals(newContent, updatedArticle.getContent());
    }

    @Test
    void deleteArticleTest() {
        int articleId = 1;

        articleService.deleteArticle(articleId);

        verify(articleRepository, times(1)).deleteById(articleId);
    }
}
