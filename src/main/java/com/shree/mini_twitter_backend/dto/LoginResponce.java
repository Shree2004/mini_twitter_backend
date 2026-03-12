package com.shree.mini_twitter_backend.dto;

import lombok.Data;

@Data
public class LoginResponce {
    String jwt;
    Long userId;

    public LoginResponce(String token, Long userId) {
    }
}
