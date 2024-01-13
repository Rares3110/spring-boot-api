package com.rares.articlehub.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String title;
    @Enumerated(EnumType.ORDINAL)
    private CategoryState state;
    @ManyToMany
    private List<Article> articles;

    public Category() {
    }

    public Category(int id,
                    String title,
                    CategoryState state,
                    List<Article> articles) {
        this.id = id;
        this.title = title;
        this.state = state;
        this.articles = articles;
    }

    public Category(String title, CategoryState state, List<Article> articles) {
        this.title = title;
        this.state = state;
        this.articles = articles;
    }

    public Category(int id, String title, CategoryState state) {
        this.id = id;
        this.title = title;
        this.state = state;
    }

    public Category(String title, CategoryState state) {
        this.title = title;
        this.state = state;
    }

    public Category(String title) {
        this.title = title;
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

    public CategoryState getState() {
        return state;
    }

    public void setState(CategoryState state) {
        this.state = state;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
