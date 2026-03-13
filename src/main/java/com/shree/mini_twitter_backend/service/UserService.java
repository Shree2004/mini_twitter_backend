package com.shree.mini_twitter_backend.service;

import com.shree.mini_twitter_backend.dto.UserDTO;
import com.shree.mini_twitter_backend.entity.*;
import com.shree.mini_twitter_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    public User registerUser(User user){
        if (userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByMobileNumber(user.getMobileNumber())){
            throw new RuntimeException("Mobile number Already exists");
        }

        return userRepository.save(user);
    }

    public User loginUser(User user){

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isEmpty()){
            throw new RuntimeException("User not found");
        }

        User dbUser = existingUser.get();

        if (!dbUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Password incorrect");
        }
        return dbUser;
    }

    public Optional<User> getUserById(Long userId){
        return userRepository.findById(userId);
    }

    public Follow followUser(String username, String usernameToFollow){

        User follower = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        User following = userRepository.findByUsername(usernameToFollow)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        if (follower.getUserId().equals(following.getUserId())){
            throw new RuntimeException("User cannot follow themselves");
        }

        if (followRepository.existsByFollowerAndFollowing(follower, following)){
            throw new RuntimeException("Already followed");
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        return followRepository.save(follow);
    }

    public Follow unFollowUser(String username, String usernameToUnfollow){

        User follower = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Follower not found"));

        User following = userRepository.findByUsername(usernameToUnfollow)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        if (follower.getUserId().equals(following.getUserId())){
            throw new RuntimeException("User cannot unfollow themselves");
        }

        Follow unfollow = followRepository
                .findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new RuntimeException("Follow relation not found"));

        followRepository.delete(unfollow);

        return unfollow;
    }

    public List<UserDTO> searchUsers(String username) {

        List<User> users = userRepository.findByUsernameContaining(username);

        return users.stream()
                .map(user -> new UserDTO(
                        user.getUserId(),
                        user.getUsername()
                ))
                .toList();
    }



}
