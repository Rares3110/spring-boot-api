package com.rares.articlehub.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String content;
    @Enumerated(EnumType.ORDINAL)
    private CommentVisibility visibility;
    @ManyToOne
    @NotNull
    private User user;
    @ManyToOne
    private Article article;
    @ManyToOne
    private Comment comment;
    @OneToMany (mappedBy = "comment")
    private List<Comment> receivedComments;

    public Comment() {
    }

    public Comment(int id, String content, CommentVisibility visibility, User user, Article article, Comment comment, List<Comment> receivedComments) {
        this.id = id;
        this.content = content;
        this.visibility = visibility;
        this.user = user;
        this.article = article;
        this.comment = comment;
        this.receivedComments = receivedComments;
    }

    public Comment(String content, CommentVisibility visibility, User user, Article article, Comment comment, List<Comment> receivedComments) {
        this.content = content;
        this.visibility = visibility;
        this.user = user;
        this.article = article;
        this.comment = comment;
        this.receivedComments = receivedComments;
    }

    public Comment(String content, CommentVisibility visibility, User user, Article article, Comment comment) {
        this.content = content;
        this.visibility = visibility;
        this.user = user;
        this.article = article;
        this.comment = comment;
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

    public User getUser() {
        return user;
    }

    public CommentVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(CommentVisibility visibility) {
        this.visibility = visibility;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Comment> getReceivedComments() {
        return receivedComments;
    }

    public void setReceivedComments(List<Comment> receivedComments) {
        this.receivedComments = receivedComments;
    }
}
