package com.shree.mini_twitter_backend.service;

import com.shree.mini_twitter_backend.dto.FeedPost;
import com.shree.mini_twitter_backend.dto.UserDTO;
import com.shree.mini_twitter_backend.entity.Follow;
import com.shree.mini_twitter_backend.entity.Post;
import com.shree.mini_twitter_backend.entity.User;
import com.shree.mini_twitter_backend.repository.FollowRepository;
import com.shree.mini_twitter_backend.repository.PostRepository;
import com.shree.mini_twitter_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class FeedService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FollowRepository followRepository;

    public List<FeedPost> feedGeneration(String username, int page, int size) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        List<Follow> follow = followRepository.findByFollower(user);

        List<Long> followedUserIds = new java.util.ArrayList<>(
                follow.stream()
                        .map(follow1 -> follow1.getFollowing().getUserId())
                        .toList()
        );

        followedUserIds.add(user.getUserId());

        Pageable pageable = PageRequest.of(page, size);

        Page<Post> postPage = postRepository
                .findByUser_UserIdInOrderByCreatedAtDesc(followedUserIds, pageable);

        List<Post> posts = postPage.getContent();

        return posts.stream().map(post -> {

            FeedPost post1 = new FeedPost();

            post1.setPostId(post.getPostId());
            post1.setContent(post.getContent());
            post1.setImageUrl(post.getImageUrl());
            post1.setCreatedAt(post.getCreatedAt());

            post1.setUser(new UserDTO(
                    post.getUser().getUserId(),
                    post.getUser().getUsername()
            ));

            post1.setLikeCount(post.getLikes().size());
            post1.setCommentCount(post.getComments().size());

            return post1;

        }).toList();
    }}
