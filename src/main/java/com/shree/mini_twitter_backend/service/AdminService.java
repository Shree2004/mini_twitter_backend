package com.shree.mini_twitter_backend.service;

import com.shree.mini_twitter_backend.entity.User;
import com.shree.mini_twitter_backend.enums.AccountStatus;
import com.shree.mini_twitter_backend.repository.PostRepository;
import com.shree.mini_twitter_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public String deletePostByAdmin(Long postId){

        if(!postRepository.existsById(postId)){
            throw new RuntimeException("Post not found");
        }

        postRepository.deleteById(postId);

        return "Post deleted by admin";
    }

    public String banUser(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAccountStatus(AccountStatus.BANNED);

        userRepository.save(user);

        return "User banned successfully";
    }

    public String activateUser(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAccountStatus(AccountStatus.ACTIVE);

        userRepository.save(user);

        return "User activated successfully";
    }
}