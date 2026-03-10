package com.shree.mini_twitter_backend.controller;

import com.shree.mini_twitter_backend.entity.Post;
import com.shree.mini_twitter_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/users/{userId}/posts")
    public Post createPost(@PathVariable Long userId,
                           @RequestParam String content,
                           @RequestParam (required = false) MultipartFile image) throws IOException {
        return postService.createPost(userId,content,image);
    }

    @GetMapping("/posts/{postId}")
    public Post getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @GetMapping("/users/{userId}/posts")
    public List<Post> getPostsByUserId(@PathVariable Long userId){
        return postService.getPostsByUserId(userId);
    }

    @DeleteMapping("/users/{userId}/posts/{postId}")
    public void deletePost(@PathVariable Long userId, @PathVariable Long postId){
        postService.deletePost(userId, postId);
    }
}
