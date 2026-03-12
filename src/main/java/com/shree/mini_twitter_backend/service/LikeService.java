package java.service;

import java.entity.Like;
import java.entity.Post;
import java.entity.User;
import java.repository.LikeRepository;
import java.repository.PostRepository;
import java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;


    public Like likePost(Long userId, Long postId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Not Found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not Found"));

        if (likeRepository.existsByUserAndPost(user,post)){
            throw new RuntimeException("You Already liked this post");
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        return likeRepository.save(like);
    }

    public void unLikePost(Long userId, Long postId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Not Found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not Found"));

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        likeRepository.delete(like);

    }

    public Long getLikesCount(Long postId){

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not Found"));

        return likeRepository.countByPost(post);
    }



}
