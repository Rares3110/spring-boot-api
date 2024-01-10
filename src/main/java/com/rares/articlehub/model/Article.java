package com.rares.articlehub.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column(length = 2000)
    private String content;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ExternalResource> externalResources;
    @ManyToMany(mappedBy = "articles")
    private List<Category> categories;
    @ManyToMany(mappedBy = "referencedIn")
    private List<Article> referencing;
    @ManyToMany
    private List<Article> referencedIn;

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

    public List<Article> getReferencing() {
        return referencing;
    }

    public void setReferencing(List<Article> following) {
        this.referencing = following;
    }

    public List<Article> getReferencedIn() {
        return referencedIn;
    }

    public void setReferencedIn(List<Article> followers) {
        this.referencedIn = followers;
    }
}
