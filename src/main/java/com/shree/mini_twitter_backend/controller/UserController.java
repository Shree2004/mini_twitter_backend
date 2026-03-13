package com.shree.mini_twitter_backend.controller;

import com.shree.mini_twitter_backend.entity.Follow;
import com.shree.mini_twitter_backend.entity.User;
import com.shree.mini_twitter_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


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
//
//    @PostMapping("/auth/login")
//    public User loginUser(@RequestBody LoginRequest request){
//
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setPassword(request.getPassword());
//
//        return userService.loginUser(user);
//    }

    @GetMapping("/me")
    public String me(Authentication authentication){
        return authentication.getName();
    }

    @GetMapping("/users/{Id}")
    public User getUserById(@PathVariable Long Id){
        return userService.getUserById(Id).orElseThrow();
    }

    @PostMapping("/users/{followingId}/follow")
    public Follow followUser(@PathVariable Long followingId,
                             Authentication authentication){

        String username = authentication.getName();

        return userService.followUser(username, followingId);
    }

    @PostMapping("/users/{followingId}/unfollow")
    public Follow unFollowUser(@PathVariable Long followingId,
                               Authentication authentication){

        String username = authentication.getName();

        return userService.unFollowUser(username, followingId);
    }

}
