package com.rares.articlehub.dto;

import com.rares.articlehub.model.ExternalResource;
import com.sun.istack.NotNull;

import javax.persistence.Column;
import java.util.List;

public class ArticleRequest {
    private String title;
    private String content;
    private int userId;

    ArticleRequest() {
    }

    public ArticleRequest(String title, String content, int userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
