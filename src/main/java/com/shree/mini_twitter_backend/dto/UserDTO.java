package com.shree.mini_twitter_backend.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long userId;
    private String username;

    public UserDTO(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

}
