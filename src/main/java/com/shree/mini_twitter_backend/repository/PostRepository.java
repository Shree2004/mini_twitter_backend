package com.shree.mini_twitter_backend.repository;

import com.shree.mini_twitter_backend.entity.Follow;
import com.shree.mini_twitter_backend.entity.Post;
import com.shree.mini_twitter_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);
    List<Post> findByUserOrderByCreatedAtDesc();
    List<Post> findAllByOrderByCreatedAtDesc(List<Long> followedUserIds);
    List<Post> findByContentContainingIgnoreCase(String keyword);
    List<Post> findByFollowersId(List<Follow> followerId);
}