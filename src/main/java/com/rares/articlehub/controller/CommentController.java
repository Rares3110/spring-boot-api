package com.rares.articlehub.controller;

import com.rares.articlehub.dto.CommentRequest;
import com.rares.articlehub.dto.CommentResponse;
import com.rares.articlehub.mapper.CommentMapper;
import com.rares.articlehub.model.Article;
import com.rares.articlehub.model.Comment;
import com.rares.articlehub.model.CommentVisibility;
import com.rares.articlehub.model.User;
import com.rares.articlehub.service.ArticleService;
import com.rares.articlehub.service.CommentService;
import com.rares.articlehub.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    private final UserService userService;

    private final ArticleService articleService;

    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService,
                             UserService userService,
                             ArticleService articleService,
                             CommentMapper commentMapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.articleService = articleService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable int id){
        return commentService.findCommentById(id)
                .map(comment -> ResponseEntity.ok().body(
                        commentMapper.convertCommentToResponse(comment)
                )).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsForComment(@PathVariable int id) {
        Optional<Comment> commentOptional = commentService.findCommentById(id);

        if(commentOptional.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(
                commentService.findCommentsForComment(id)
                        .stream()
                        .map(commentMapper::convertCommentToResponse)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/new")
    public ResponseEntity<CommentResponse> saveComment(@RequestBody CommentRequest commentRequest) {
        Optional<User> userOptional = userService.findUserById(commentRequest.getUserId());

        if(userOptional.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<Article> articleOptional = Optional.empty();
        if(commentRequest.getArticleId() != null)
            articleOptional = articleService.findArticleById(commentRequest.getArticleId());

        Optional<Comment> parentCommentOptional = Optional.empty();
        if(commentRequest.getCommentId() != null)
            parentCommentOptional = commentService.findCommentById(commentRequest.getCommentId());

        Optional<Comment> result = commentService.saveComment(commentMapper.convertRequestToComment(
                commentRequest,
                userOptional.get(),
                articleOptional.orElse(null),
                parentCommentOptional.orElse(null)
        ));

        return result.map(comment -> ResponseEntity.ok().body(
                commentMapper.convertCommentToResponse(comment))
                )
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}/update-content")
    public ResponseEntity<CommentResponse> updateCommentContent(@PathVariable int id, @RequestBody String content) {
        Optional<Comment> commentOptional = commentService.findCommentById(id);

        if(commentOptional.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(
                commentMapper.convertCommentToResponse(
                    commentService.updateCommentContent(id, content)
                )
        );
    }

    @PutMapping("/{id}/update-visibility")
    public ResponseEntity<CommentResponse> updateCommentVisibility(@PathVariable int id, @RequestBody CommentVisibility visibility) {
        Optional<Comment> commentOptional = commentService.findCommentById(id);

        if(commentOptional.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(
                commentMapper.convertCommentToResponse(
                        commentService.updateCommentVisibility(id, visibility)
                )
        );
    }
}
