package com.shree.mini_twitter_backend.controller;

import com.shree.mini_twitter_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @DeleteMapping("/posts/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletePost(@PathVariable Long postId){
        return adminService.deletePostByAdmin(postId);
    }

    @PutMapping("/users/{userId}/ban")
    @PreAuthorize("hasRole('ADMIN')")
    public String banUser(@PathVariable Long userId){
        return adminService.banUser(userId);
    }

    @PutMapping("/users/{userId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public String activateUser(@PathVariable Long userId){
        return adminService.activateUser(userId);
    }
}