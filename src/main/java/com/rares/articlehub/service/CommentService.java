package com.rares.articlehub.service;

import com.rares.articlehub.model.Comment;
import com.rares.articlehub.model.CommentVisibility;
import com.rares.articlehub.repository.CommentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    public final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Optional<Comment> findCommentById(int id) {
        return commentRepository.findById(id);
    }

    @Transactional
    public List<Comment> findCommentsForComment(int id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);

        if(commentOptional.isEmpty())
            return Collections.emptyList();

        return new ArrayList<>(commentOptional.get().getReceivedComments());
    }

    public Optional<Comment> saveComment(Comment comment) {
        if(comment.getArticle() == null && comment.getComment() == null)
            return Optional.empty();
        if(comment.getArticle() != null && comment.getComment() != null)
            return Optional.empty();

        return Optional.of(commentRepository.save(comment));
    }

    @Transactional
    public Comment updateCommentContent(int id, String content) {
        Optional<Comment> commentOptional = commentRepository.findById(id);

        if(commentOptional.isEmpty())
            throw new IllegalStateException("comment with id " + id + " not found");

        commentOptional.get().setContent(content);
        return commentOptional.get();
    }

    @Transactional
    public Comment updateCommentVisibility(int id, CommentVisibility visibility) {
        Optional<Comment> commentOptional = commentRepository.findById(id);

        if(commentOptional.isEmpty())
            throw new IllegalStateException("comment with id " + id + " not found");

        commentOptional.get().setVisibility(visibility);
        return commentOptional.get();
    }
}
