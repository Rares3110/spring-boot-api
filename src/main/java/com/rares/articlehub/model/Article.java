package com.rares.articlehub.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String title;
    @Column(length = 2000)
    @NotNull
    private String content;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ExternalResource> externalResources;
    @ManyToMany(mappedBy = "articles")
    private List<Category> categories;
    @ManyToMany(mappedBy = "referencedIn")
    private List<Article> referencing;
    @ManyToMany
    private List<Article> referencedIn;
    @ManyToOne
    @NotNull
    private User user;
    @ManyToMany
    private List<Library> libraries;
    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    public Article() {
    }

    public Article(int id,
                   String title,
                   String content,
                   List<ExternalResource> externalResources,
                   List<Category> categories,
                   List<Article> referencing,
                   List<Article> referencedIn,
                   User user,
                   List<Library> libraries,
                   List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.externalResources = externalResources;
        this.categories = categories;
        this.referencing = referencing;
        this.referencedIn = referencedIn;
        this.user = user;
        this.libraries = libraries;
        this.comments = comments;
    }

    public Article(String title,
                   String content,
                   List<ExternalResource> externalResources,
                   List<Category> categories,
                   List<Article> referencing,
                   List<Article> referencedIn,
                   User user,
                   List<Library> libraries,
                   List<Comment> comments) {
        this.title = title;
        this.content = content;
        this.externalResources = externalResources;
        this.categories = categories;
        this.referencing = referencing;
        this.referencedIn = referencedIn;
        this.user = user;
        this.libraries = libraries;
        this.comments = comments;
    }

    public Article(String title,
                   String content,
                   User user) {
        this.title = title;
        this.content = content;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(List<Library> libraries) {
        this.libraries = libraries;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
