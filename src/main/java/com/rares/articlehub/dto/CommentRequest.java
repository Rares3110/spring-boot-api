package com.rares.articlehub.dto;

public class CommentRequest {
    private String content;
    private int userId;
    private Integer articleId;
    private Integer commentId;

    public CommentRequest(){
    }

    public CommentRequest(String content, int userId, Integer articleId, Integer commentId){
        this.content = content;
        this.userId = userId;
        this.articleId = articleId;
        this.commentId = commentId;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public Integer getArticleId(){
        return articleId;
    }

    public void setArticleId(Integer articleId){
        this.articleId = articleId;
    }

    public Integer getCommentId(){
        return commentId;
    }

    public void setCommentId(Integer commentId){
        this.commentId = commentId;
    }
}
