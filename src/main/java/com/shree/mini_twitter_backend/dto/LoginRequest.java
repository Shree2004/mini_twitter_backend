package com.shree.mini_twitter_backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
    }

}
