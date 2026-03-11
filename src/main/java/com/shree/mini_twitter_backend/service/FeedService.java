package com.shree.mini_twitter_backend.service;

import com.shree.mini_twitter_backend.entity.Follow;
import com.shree.mini_twitter_backend.entity.Post;
import com.shree.mini_twitter_backend.entity.User;
import com.shree.mini_twitter_backend.repository.FollowRepository;
import com.shree.mini_twitter_backend.repository.PostRepository;
import com.shree.mini_twitter_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FollowRepository followRepository;

    public List<Post> feedGeneration(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Not Found"));

        List<Follow> follow = followRepository.findByFollowers(user);

        List<Long> followedUserIds = new java.util.ArrayList<>
                (
                follow.stream()
                .map(follow1 -> follow1.getFollowing().getUserId())
                .toList()
                );

        followedUserIds.add(userId);

        return postRepository.findAllByOrderByCreatedAtDesc(followedUserIds);

    }

}
