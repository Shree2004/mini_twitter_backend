package com.shree.mini_twitter_backend.controller;

import com.shree.mini_twitter_backend.dto.FeedPost;
import com.shree.mini_twitter_backend.entity.Post;
import com.shree.mini_twitter_backend.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.config.annotation.web.oauth2.login.OAuth2LoginSecurityMarker;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class FeedController {

    @Autowired
    private FeedService feedService;

    @GetMapping("/{userId}/feeds")
    public Page<FeedPost> feedGeneration(@PathVariable Long userId, @RequestParam int page, @RequestParam int size){
        return feedService.feedGeneration(userId, page , size);
    }

}
