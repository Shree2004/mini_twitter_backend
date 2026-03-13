package com.shree.mini_twitter_backend.service;

import com.shree.mini_twitter_backend.dto.FeedPost;
import com.shree.mini_twitter_backend.dto.ProfileDTO;
import com.shree.mini_twitter_backend.dto.UserDTO;
import com.shree.mini_twitter_backend.entity.Follow;
import com.shree.mini_twitter_backend.entity.Post;
import com.shree.mini_twitter_backend.entity.User;
import com.shree.mini_twitter_backend.repository.FollowRepository;
import com.shree.mini_twitter_backend.repository.PostRepository;
import com.shree.mini_twitter_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;

    public User registerUser(User user){

        if (userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByMobileNumber(user.getMobileNumber())){
            throw new RuntimeException("Mobile number already exists");
        }

        return userRepository.save(user);
    }

    public User loginUser(User user){

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isEmpty()){
            throw new RuntimeException("User not found");
        }

        User dbUser = existingUser.get();

        if (!dbUser.getPassword().equals(user.getPassword())){
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

    public List<UserDTO> searchUsers(String username){

        List<User> users = userRepository.findByUsernameContaining(username);

        return users.stream()
                .map(user -> new UserDTO(
                        user.getUserId(),
                        user.getUsername()
                ))
                .toList();
    }

    public ProfileDTO getProfile(String username){

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        long postCount = postRepository.countByUser(user);
        long followerCount = followRepository.countByFollowing(user);
        long followingCount = followRepository.countByFollower(user);

        List<Post> userPosts = postRepository.findByUser(user);

        List<FeedPost> postDTOs = userPosts.stream()
                .map(post -> {
                    FeedPost dto = new FeedPost();

                    dto.setPostId(post.getPostId());
                    dto.setContent(post.getContent());
                    dto.setImageUrl(post.getImageUrl());
                    dto.setCreatedAt(post.getCreatedAt());
                    dto.setLikeCount(post.getLikes().size());
                    dto.setCommentCount(post.getComments().size());

                    return dto;
                })
                .toList();

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setUsername(user.getUsername());
        profileDTO.setBio(user.getBio());
        profileDTO.setPostCount(postCount);
        profileDTO.setFollowerCount(followerCount);
        profileDTO.setFollowingCount(followingCount);
        profileDTO.setPosts(postDTOs);

        return profileDTO;
    }

}