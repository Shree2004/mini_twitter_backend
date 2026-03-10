package com.shree.mini_twitter_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "likes", uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "post_id"}) } )

public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
