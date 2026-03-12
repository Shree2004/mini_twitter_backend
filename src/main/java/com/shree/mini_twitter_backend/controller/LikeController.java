package java.controller;

import java.entity.Like;
import java.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/users/{userId}/likes/{postId}")
    public Like likePost(@PathVariable Long userId, @PathVariable Long postId) {
        return likeService.likePost(userId, postId);
    }

    @DeleteMapping("/users/{userId}/unlikes/{postId}")
    public void unLikePost(@PathVariable Long userId, @PathVariable Long postId) {
        likeService.unLikePost(userId, postId);
    }

    @GetMapping("/posts/{postId}/likes")
    public Long getLikesCount(@PathVariable Long postId) {
        return likeService.getLikesCount(postId);
    }
}
