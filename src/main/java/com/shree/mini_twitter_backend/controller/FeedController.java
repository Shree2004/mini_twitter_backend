package com.shree.mini_twitter_backend.controller;

import com.shree.mini_twitter_backend.dto.FeedPost;
import com.shree.mini_twitter_backend.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class FeedController {

    @Autowired
    private FeedService feedService;

    @GetMapping("/feed")
    public List<FeedPost> feedGeneration(Authentication authentication,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size){

        String username = authentication.getName();

        return feedService.feedGeneration(username, page, size);
    }

}
