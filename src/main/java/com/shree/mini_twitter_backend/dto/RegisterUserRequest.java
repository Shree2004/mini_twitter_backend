package com.shree.mini_twitter_backend.dto;

import com.shree.mini_twitter_backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {

    private String username;
    private String email;
    private String mobileNumber;
    private String password;
    private String bio;

    public RegisterUserRequest(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.mobileNumber = user.getMobileNumber();
        this.password = user.getPassword();
        this.bio = user.getBio();
    }
}
