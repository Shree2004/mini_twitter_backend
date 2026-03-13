package com.shree.mini_twitter_backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedPost {
    private Long postId;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private UserDTO user;
    private int likeCount;
    private int commentCount;

    public FeedPost(Long postId, String content, String imageUrl, LocalDateTime createdAt, String username, int size, int size1) {
    }

    public FeedPost() {

    }
}
