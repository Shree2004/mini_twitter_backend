package com.shree.mini_twitter_backend.controller;

import com.shree.mini_twitter_backend.entity.Like;
import com.shree.mini_twitter_backend.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/posts/{postId}/like")
    public Like likePost(@PathVariable Long postId,
                         Authentication authentication) {

        String username = authentication.getName();

        return likeService.likePost(username, postId);
    }

    @DeleteMapping("/posts/{postId}/unlike")
    public void unLikePost(@PathVariable Long postId,
                           Authentication authentication) {

        String username = authentication.getName();

        likeService.unLikePost(username, postId);
    }

    @GetMapping("/posts/{postId}/likes")
    public Long getLikesCount(@PathVariable Long postId) {
        return likeService.getLikesCount(postId);
    }
}
