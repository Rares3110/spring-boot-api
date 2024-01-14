package com.rares.articlehub.dto;

import com.rares.articlehub.model.CommentVisibility;

public class CommentResponse {
    private int id;
    private String content;

    public CommentResponse() {
    }

    public CommentResponse(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
