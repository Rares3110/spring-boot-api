package com.rares.articlehub.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.net.MalformedURLException;
import java.net.URL;

@Entity
public class ExternalResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int articleIndex;
    @NotNull
    private String urlAsString;
    @ManyToOne
    @NotNull
    private Article article;

    public ExternalResource() {
    }

    public ExternalResource(int id, int articleIndex, String urlAsString, Article article) {
        this.id = id;
        this.articleIndex = articleIndex;
        this.urlAsString = urlAsString;
        this.article = article;
    }

    public ExternalResource(int articleIndex, String urlAsString, Article article) {
        this.articleIndex = articleIndex;
        this.urlAsString = urlAsString;
        this.article = article;
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

    public URL getUrl() throws MalformedURLException {
        return new URL(urlAsString);
    }

    public void setUrl(URL url) {
        this.urlAsString = url.toString();
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
