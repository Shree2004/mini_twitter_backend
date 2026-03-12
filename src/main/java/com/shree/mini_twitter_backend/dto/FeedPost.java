package java.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedPost {
    private Long postId;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private UserDTO user;
    private int likeCount;
    private int commentCount;
}
