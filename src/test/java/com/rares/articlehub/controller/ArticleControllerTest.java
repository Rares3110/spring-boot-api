package com.rares.articlehub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rares.articlehub.dto.ArticleRequest;
import com.rares.articlehub.dto.ArticleResponse;
import com.rares.articlehub.mapper.ArticleMapper;
import com.rares.articlehub.mapper.CategoryMapper;
import com.rares.articlehub.model.Article;
import com.rares.articlehub.model.User;
import com.rares.articlehub.model.UserRole;
import com.rares.articlehub.service.ArticleService;
import com.rares.articlehub.service.CategoryService;
import com.rares.articlehub.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
}
