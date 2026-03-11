package com.shree.mini_twitter_backend.controller;

import com.shree.mini_twitter_backend.entity.Post;
import com.shree.mini_twitter_backend.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.oauth2.login.OAuth2LoginSecurityMarker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class FeedController {

    @Autowired
    private FeedService feedService;

    @GetMapping("/{userId}/feeds")
    public List<Post> feedGeneration(@PathVariable Long userId){
        return feedService.feedGeneration(userId);
    }

}
