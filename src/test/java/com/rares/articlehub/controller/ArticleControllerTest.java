package com.rares.articlehub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rares.articlehub.dto.ArticleHeaderResponse;
import com.rares.articlehub.dto.ArticleRequest;
import com.rares.articlehub.dto.ArticleResponse;
import com.rares.articlehub.dto.CategoryResponse;
import com.rares.articlehub.mapper.ArticleMapper;
import com.rares.articlehub.mapper.CategoryMapper;
import com.rares.articlehub.model.*;
import com.rares.articlehub.service.ArticleService;
import com.rares.articlehub.service.CategoryService;
import com.rares.articlehub.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {
    @MockBean
    private UserService userService;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ArticleMapper articleMapper;

    @MockBean
    private CategoryMapper categoryMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getArticleByIdTest() throws Exception {
        int articleId = 1;

        Article mockArticle = new Article();
        mockArticle.setId(articleId);
        ArticleResponse mockArticleResponse = new ArticleResponse(articleId, "Mock Title", "Mock Content", 1);

        when(articleService.findArticleById(articleId)).thenReturn(Optional.of(mockArticle));
        when(articleMapper.convertArticleToResponse(mockArticle)).thenReturn(mockArticleResponse);

        mockMvc.perform(get("/article/{id}", articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(mockArticleResponse.getId()))
                .andExpect(jsonPath("$.title").value(mockArticleResponse.getTitle()))
                .andExpect(jsonPath("$.content").value(mockArticleResponse.getContent()))
                .andExpect(jsonPath("$.userId").value(mockArticleResponse.getUserId()));
    }

    @Test
    public void getArticleHeaderPageTest() throws Exception {
        int page = 0;
        int size = 10;

        User mockUser = new User(
                1,
                "Rares",
                "Test@yahoo.ro",
                "TestTest@!12",
                UserRole.USER
        );
        List<Article> mockArticles = Arrays.asList(
                new Article(1,"Title 1", "Content 1", mockUser),
                new Article(2,"Title 2", "Content 2", mockUser)
        );
        List<ArticleHeaderResponse> mockArticleHeaderResponses = Arrays.asList(
                new ArticleHeaderResponse(
                        mockArticles.get(0).getId(),
                        mockArticles.get(0).getTitle(),
                        mockArticles.get(0).getUser().getUsername()),
                new ArticleHeaderResponse(
                        mockArticles.get(1).getId(),
                        mockArticles.get(1).getTitle(),
                        mockArticles.get(1).getUser().getUsername())
        );

        Page<Article> mockArticlePage = new PageImpl<>(mockArticles);

        when(articleService.findPage(PageRequest.of(page, size))).thenReturn(mockArticlePage);
        when(articleMapper.convertArticleToHeaderResponse(mockArticles.get(0))).thenReturn(mockArticleHeaderResponses.get(0));
        when(articleMapper.convertArticleToHeaderResponse(mockArticles.get(1))).thenReturn(mockArticleHeaderResponses.get(1));

        mockMvc.perform(get("/article/headers-list/")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(mockArticles.size())))
                .andExpect(jsonPath("$[0].title").value(mockArticles.get(0).getTitle()))
                .andExpect(jsonPath("$[0].username").value(mockArticles.get(0).getUser().getUsername()))
                .andExpect(jsonPath("$[1].title").value(mockArticles.get(1).getTitle()))
                .andExpect(jsonPath("$[1].username").value(mockArticles.get(1).getUser().getUsername()));
    }

    @Test
    public void getArticleCategoriesTest() throws Exception {
        int articleId = 1;

        Article mockArticle = new Article();
        mockArticle.setId(articleId);

        List<Category> mockCategories = Arrays.asList(
                new Category(1, "Category1", CategoryState.ACCEPTED),
                new Category(2, "Category2", CategoryState.ACCEPTED)
        );

        List<CategoryResponse> mockCategoryResponses = Arrays.asList(
                new CategoryResponse(
                        mockCategories.get(0).getId(),
                        mockCategories.get(0).getTitle()),
                new CategoryResponse(
                        mockCategories.get(1).getId(),
                        mockCategories.get(1).getTitle())
        );

        when(articleService.findArticleById(articleId)).thenReturn(Optional.of(mockArticle));
        when(articleService.getCategoriesForArticle(articleId)).thenReturn(mockCategories);
        when(categoryMapper.convertCategoryToResponse(any(Category.class)))
                .thenAnswer(invocation -> {
                    Category category = invocation.getArgument(0);
                    return new CategoryResponse(category.getId(), category.getTitle());
                });

        mockMvc.perform(get("/article/{id}/accepted-categories", articleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(mockCategories.size())))
                .andExpect(jsonPath("$[0].id").value(mockCategoryResponses.get(0).getId()))
                .andExpect(jsonPath("$[0].title").value(mockCategoryResponses.get(0).getTitle()))
                .andExpect(jsonPath("$[1].id").value(mockCategoryResponses.get(1).getId()))
                .andExpect(jsonPath("$[1].title").value(mockCategoryResponses.get(1).getTitle()));
    }

    @Test
    public void saveArticleTest() throws Exception {
        User mockUser = new User(
                1,
                "Test@yahoo.ro",
                "Test",
                "TestTest@!12",
                UserRole.USER
        );
        Article mockArticle = new Article(
                1,
                "Title",
                "Content",
                mockUser
        );

        ArticleRequest articleRequest = new ArticleRequest(mockArticle.getTitle(), mockArticle.getContent(), mockUser.getId());
        ArticleResponse articleResponse = new ArticleResponse(mockArticle.getId(), mockArticle.getTitle(), mockArticle.getContent(), mockUser.getId());

        when(userService.findUserById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(mockUser));
        when(articleMapper.convertRequestToArticle(articleRequest, mockUser)).thenReturn(mockArticle);
        when(articleService.saveArticle(mockArticle)).thenReturn(mockArticle);
        when(articleMapper.convertArticleToResponse(mockArticle)).thenReturn(articleResponse);

        mockMvc.perform(post("/article/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleRequest))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void updateArticleTest() throws Exception {
        Article mockArticle = new Article(1, "Old Title", "Old Content", new User());
        String updatedTitle = "New Title";
        String updatedContent = "New Content";
        Article updatedArticle = new Article(1, updatedTitle, updatedContent, mockArticle.getUser());

        ArticleResponse updatedArticleResponse = new ArticleResponse(
                updatedArticle.getId(), updatedArticle.getTitle(), updatedArticle.getContent(), updatedArticle.getUser().getId()
        );

        when(articleService.findArticleById(1)).thenReturn(Optional.of(mockArticle));
        when(articleService.updateArticle(1, updatedTitle, updatedContent)).thenReturn(updatedArticle);
        when(articleMapper.convertArticleToResponse(updatedArticle)).thenReturn(updatedArticleResponse);

        mockMvc.perform(put("/article/1")
                        .param("title", updatedTitle)
                        .param("content", updatedContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedArticle.getId()))
                .andExpect(jsonPath("$.title").value(updatedArticle.getTitle()))
                .andExpect(jsonPath("$.content").value(updatedArticle.getContent()))
                .andExpect(jsonPath("$.userId").value(updatedArticle.getUser().getId()));
    }

    @Test
    public void deleteArticleTest() throws Exception {
        int articleId = 1;

        // Mock service calls
        when(articleService.findArticleById(articleId)).thenReturn(Optional.of(new Article()));

        mockMvc.perform(delete("/article/" + articleId))
                .andExpect(status().isOk());
    }
}
