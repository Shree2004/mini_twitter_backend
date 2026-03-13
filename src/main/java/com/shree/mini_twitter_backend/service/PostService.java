package com.shree.mini_twitter_backend.service;

import com.shree.mini_twitter_backend.config.CloudinaryConfig;
import com.shree.mini_twitter_backend.entity.Post;
import com.shree.mini_twitter_backend.entity.User;
import com.shree.mini_twitter_backend.repository.PostRepository;
import com.shree.mini_twitter_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public Post createPost(String username, String content, MultipartFile image) throws IOException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            imageUrl = cloudinaryService.uploadImage(image);
        }

        Post post = new Post();
        post.setContent(content);
        post.setImageUrl(imageUrl);
        post.setUser(user);

        return postRepository.save(post);
    }

    public Post getPostById(Long postId){
        Post post = postRepository.findById(postId).
                orElseThrow(() -> new RuntimeException("post not found"));
        return post;
    }

    public List<Post> getPostsByUserId(Long userId){
        User user = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("user not found"));
        return postRepository.findByUser(user);
    }

    public void deletePost(String username, Long postId) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = getPostById(postId);

        boolean isOwner = post.getUser().getUserId().equals(user.getUserId());
        boolean isAdmin = user.getRole().name().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You cannot delete this post");
        }

        postRepository.delete(post);
    }


}
