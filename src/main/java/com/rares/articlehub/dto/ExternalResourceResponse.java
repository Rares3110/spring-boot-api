package com.rares.articlehub.dto;

import java.net.URL;

public class ExternalResourceResponse {
    private int id;
    private int articleIndex;
    private URL url;
    private int articleId;

    public ExternalResourceResponse() {
    }

    public ExternalResourceResponse(int id, int articleIndex, URL url, int articleId) {
        this.id = id;
        this.articleIndex = articleIndex;
        this.url = url;
        this.articleId = articleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleIndex() {
        return articleIndex;
    }

    public void setArticleIndex(int articleIndex) {
        this.articleIndex = articleIndex;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
