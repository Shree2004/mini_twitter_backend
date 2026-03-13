package com.shree.mini_twitter_backend.service;


import com.shree.mini_twitter_backend.entity.Comment;
import com.shree.mini_twitter_backend.entity.Post;
import com.shree.mini_twitter_backend.entity.User;
import com.shree.mini_twitter_backend.repository.CommentRepository;
import com.shree.mini_twitter_backend.repository.PostRepository;
import com.shree.mini_twitter_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    public Comment createComment(String username, Long postId, String content){

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not Found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not Found"));

        return commentRepository.findByPostPostId(postId);
    }

    public void deleteComment(String username, Long postId, Long commentId){

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getPost().getPostId().equals(postId)) {
            throw new RuntimeException("Comment does not belong to this post");
        }

        boolean isOwner = comment.getUser().getUserId().equals(user.getUserId());
        boolean isAdmin = user.getRole().name().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You cannot delete this comment");
        }

        commentRepository.delete(comment);
    }

}
