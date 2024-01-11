package com.rares.articlehub.dto;

import com.sun.istack.NotNull;

public class ArticleHeaderResponse {
    private int id;
    @NotNull
    private String title;
    @NotNull
    private String username;

    public ArticleHeaderResponse(int id, String title, String username) {
        this.id = id;
        this.title = title;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
