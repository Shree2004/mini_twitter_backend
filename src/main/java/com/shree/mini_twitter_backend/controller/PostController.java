package com.shree.mini_twitter_backend.controller;

import com.shree.mini_twitter_backend.entity.Post;
import com.shree.mini_twitter_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    public Post createPost(@RequestParam String content,
                           @RequestParam(required = false) MultipartFile image,
                           Authentication authentication) throws IOException {

        String username = authentication.getName();

        return postService.createPost(username, content, image);
    }

    @GetMapping("/posts/{postId}")
    public Post getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @GetMapping("/users/{userId}/posts")
    public List<Post> getPostsByUserId(@PathVariable Long userId){
        return postService.getPostsByUserId(userId);
    }

    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable Long postId,
                             Authentication authentication){

        String username = authentication.getName();

        postService.deletePost(username, postId);

        return "Post deleted successfully";
    }
}
