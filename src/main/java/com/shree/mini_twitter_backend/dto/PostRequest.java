package com.shree.mini_twitter_backend.dto;

import lombok.Data;

@Data
public class PostRequest {

    private String content;
    private String imageUrl;

}