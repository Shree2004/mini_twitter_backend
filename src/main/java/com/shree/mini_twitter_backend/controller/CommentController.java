package com.shree.mini_twitter_backend.controller;

import com.shree.mini_twitter_backend.entity.Comment;
import com.shree.mini_twitter_backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public Comment createComment(@PathVariable Long postId,
                                 @RequestParam String content,
                                 Authentication authentication) {

        String username = authentication.getName();

        return commentService.createComment(username, postId, content);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long postId,
                              @PathVariable Long commentId,
                              Authentication authentication) {

        String username = authentication.getName();

        commentService.deleteComment(username, postId, commentId);
    }
}