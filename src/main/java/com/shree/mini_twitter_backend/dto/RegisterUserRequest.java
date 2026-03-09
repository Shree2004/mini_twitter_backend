package com.shree.mini_twitter_backend.dto;

import lombok.Data;

@Data
public class RegisterUserRequest {

    private String username;
    private String email;
    private String mobileNumber;
    private String password;
    private String bio;
}
