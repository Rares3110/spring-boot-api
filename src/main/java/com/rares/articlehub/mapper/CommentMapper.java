package com.rares.articlehub.mapper;

import com.rares.articlehub.dto.CommentRequest;
import com.rares.articlehub.dto.CommentResponse;
import com.rares.articlehub.model.Article;
import com.rares.articlehub.model.Comment;
import com.rares.articlehub.model.CommentVisibility;
import com.rares.articlehub.model.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentResponse convertCommentToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent()
        );
    }

    public Comment convertRequestToComment(CommentRequest commentRequest,
                                           User user,
                                           Article article,
                                           Comment comment) {
        return new Comment(
                commentRequest.getContent(),
                CommentVisibility.VISIBLE,
                user,
                article,
                comment
        );
    }
}
