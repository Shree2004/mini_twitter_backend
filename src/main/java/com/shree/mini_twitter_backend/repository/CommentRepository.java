package com.shree.mini_twitter_backend.repository;

import com.shree.mini_twitter_backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostPostId(Long postId);

}
