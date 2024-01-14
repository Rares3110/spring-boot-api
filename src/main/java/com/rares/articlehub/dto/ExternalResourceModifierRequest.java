package com.rares.articlehub.dto;

import java.net.URL;

public class ExternalResourceModifierRequest {
    private int articleIndex;
    private URL url;

    public ExternalResourceModifierRequest() {
    }

    public ExternalResourceModifierRequest(int articleIndex, URL url) {
        this.articleIndex = articleIndex;
        this.url = url;
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
}
