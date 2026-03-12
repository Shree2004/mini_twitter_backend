package java.controller;

import java.entity.Comment;
import java.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/users/{userId}/posts/{postId}/comments")
    public Comment createComment(@PathVariable Long userId, @PathVariable Long postId, @RequestParam String content) {
        return commentService.createComment(userId, postId, content);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }

    @DeleteMapping("/users/{userId}/posts/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long userId, @PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(userId, postId, commentId);
    }
}