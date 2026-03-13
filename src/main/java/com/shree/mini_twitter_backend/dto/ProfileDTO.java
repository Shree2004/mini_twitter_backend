package com.shree.mini_twitter_backend.dto;

import lombok.Data;

import java.util.List;
@Data
public class ProfileDTO {

    private String username;
    private String bio;

    private long postCount;
    private long followerCount;
    private long followingCount;

    private List<FeedPost> posts;

    public ProfileDTO(String username,
                      String bio,
                      long postCount,
                      long followerCount,
                      long followingCount,
                      List<FeedPost> posts) {

        this.username = username;
        this.bio = bio;
        this.postCount = postCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.posts = posts;
    }

    public ProfileDTO() {

    }

    public String getUsername() { return username; }
    public String getBio() { return bio; }
    public long getPostCount() { return postCount; }
    public long getFollowerCount() { return followerCount; }
    public long getFollowingCount() { return followingCount; }
    public List<FeedPost> getPosts() { return posts; }
}