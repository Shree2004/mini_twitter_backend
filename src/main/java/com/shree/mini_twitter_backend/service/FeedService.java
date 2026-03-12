package java.service;

import java.dto.FeedPost;
import java.dto.UserDTO;
import java.entity.Follow;
import java.entity.Post;
import java.entity.User;
import java.repository.FollowRepository;
import java.repository.PostRepository;
import java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FollowRepository followRepository;

    public List<FeedPost> feedGeneration(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        List<Follow> follow = followRepository.findByFollower(user);

        List<Long> followedUserIds = new java.util.ArrayList<>(
                follow.stream()
                        .map(follow1 -> follow1.getFollowing().getUserId())
                        .toList()
        );

        followedUserIds.add(userId);

        List<Post> posts = postRepository
                .findByUser_UserIdInOrderByCreatedAtDesc(followedUserIds);

        return posts.stream().map(post -> {

            FeedPost post1 = new FeedPost();

            post1.setPostId(post.getPostId());
            post1.setContent(post.getContent());
            post1.setImageUrl(post.getImageUrl());
            post1.setCreatedAt(post.getCreatedAt());

            post1.setUser(new UserDTO(
                    post.getUser().getUserId(),
                    post.getUser().getUsername()
            ));

            post1.setLikeCount(post.getLikes().size());
            post1.setCommentCount(post.getComments().size());

            return post1;

        }).toList();
    }}
