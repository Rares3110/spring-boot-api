package com.rares.articlehub.dto;

import java.net.URL;

public class ExternalResourceRequest {
    private int articleIndex;
    private URL url;
    private int articleId;

    public ExternalResourceRequest() {
    }

    public ExternalResourceRequest(int articleIndex, URL url, int articleId) {
        this.articleIndex = articleIndex;
        this.url = url;
        this.articleId = articleId;
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
