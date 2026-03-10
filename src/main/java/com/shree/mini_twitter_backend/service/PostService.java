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

    public Post createPost(Long userId, String content, MultipartFile image) throws IOException {

        User user = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("user not found"));

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

    public void deletePost(Long userId, Long postId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("user not found"));
        Post delete = getPostById(postId);
        if (!delete.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You cannot delete someone else's post");
        }
        postRepository.delete(delete);
    }


}
