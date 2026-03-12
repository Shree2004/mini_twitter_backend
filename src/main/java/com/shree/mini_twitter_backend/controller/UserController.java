package java.controller;

import java.entity.Follow;
import java.entity.User;
import java.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id).orElseThrow();
    }

    @PostMapping("/{followerId}/follow/{followingId}")
    public Follow followUser(@PathVariable Long followerId,
                             @PathVariable Long followingId){

        return userService.followUser(followerId, followingId);
    }

    @PostMapping("/{followerId}/unfollow/{followingId}")
    public Follow unFollowUser(@PathVariable Long followerId,
                               @PathVariable Long followingId){

        return userService.unFollowUser(followerId, followingId);
    }
}