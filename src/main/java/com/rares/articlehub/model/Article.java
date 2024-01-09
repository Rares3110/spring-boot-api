package com.rares.articlehub.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ExternalResource> externalResources;
    @ManyToMany(mappedBy = "articles")
    private List<Category> categories;
    @ManyToMany(mappedBy = "followers")
    private List<Article> following;

    @ManyToMany
    private List<Article> followers;

    public Article() {
    }

    public Article(int id, String title, String content, List<ExternalResource> externalResources) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.externalResources = externalResources;
    }

    public Article(String title, String content, List<ExternalResource> externalResources) {
        this.title = title;
        this.content = content;
        this.externalResources = externalResources;
    }

    public int getId() {
        return id;
    }

    public void setId(int articleId) {
        this.id = articleId;
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

    public List<ExternalResource> getExternalResources() {
        return externalResources;
    }

    public void setExternalResources(List<ExternalResource> externalResources) {
        this.externalResources = externalResources;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Article> getFollowing() {
        return following;
    }

    public void setFollowing(List<Article> following) {
        this.following = following;
    }

    public List<Article> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Article> followers) {
        this.followers = followers;
    }
}
