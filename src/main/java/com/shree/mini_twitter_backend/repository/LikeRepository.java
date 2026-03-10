package com.shree.mini_twitter_backend.repository;

import com.shree.mini_twitter_backend.entity.Like;
import com.shree.mini_twitter_backend.entity.Post;
import com.shree.mini_twitter_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPost(User user, Post post);
    Long countByPost(Long postId);
    Optional<Like> findByUserAndPost(User user, Post post);
}
