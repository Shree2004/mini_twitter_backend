package com.shree.mini_twitter_backend.service;

import com.shree.mini_twitter_backend.entity.Post;
import com.shree.mini_twitter_backend.entity.User;
import com.shree.mini_twitter_backend.repository.PostRepository;
import com.shree.mini_twitter_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public Post createPost(Long userId, Post post){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
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
