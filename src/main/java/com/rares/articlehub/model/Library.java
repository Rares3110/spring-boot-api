package com.rares.articlehub.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String title;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private LibraryType type;
    @ManyToMany(mappedBy = "libraries")
    private List<Article> articles;
    @ManyToOne
    @NotNull
    private User user;

    public Library() {
    }

    public Library(int id, String title, LibraryType type, List<Article> articles, User user) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.articles = articles;
        this.user = user;
    }

    public Library(String title, LibraryType type, List<Article> articles, User user) {
        this.title = title;
        this.type = type;
        this.articles = articles;
        this.user = user;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LibraryType getType() {
        return type;
    }

    public void setType(LibraryType type) {
        this.type = type;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
