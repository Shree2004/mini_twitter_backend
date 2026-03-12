package com.shree.mini_twitter_backend.controller;

import com.shree.mini_twitter_backend.dto.LoginRequest;
import com.shree.mini_twitter_backend.dto.RegisterUserRequest;
import com.shree.mini_twitter_backend.entity.Follow;
import com.shree.mini_twitter_backend.entity.User;
import com.shree.mini_twitter_backend.enums.AccountStatus;
import com.shree.mini_twitter_backend.enums.role;
import com.shree.mini_twitter_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/auth/register")
//    public User registerUser(@RequestBody RegisterUserRequest request){
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setEmail(request.getEmail());
//        user.setMobileNumber(request.getMobileNumber());
//        user.setPassword(request.getPassword());
//        user.setBio(request.getBio());
//        user.setAccountStatus(AccountStatus.ACTIVE);
//        user.setRole(role.USER);
//
//        return userService.registerUser(user);
//    }

    @GetMapping("/users/{Id}")
    public User getUserById(@PathVariable Long Id){
        return userService.getUserById(Id).orElseThrow();
    }

    @PostMapping("/users/{followerId}/follow/{followingId}")
    public Follow followUser(@PathVariable Long followerId, @PathVariable Long followingId){
        return userService.followUser(followerId, followingId);
    }

    @PostMapping("/users/{followerId}/unfollow/{followingId}")
    public Follow unFollowUser(@PathVariable Long followerId,@PathVariable Long followingId){
        return userService.unFollowUser(followerId, followingId);
    }

}
