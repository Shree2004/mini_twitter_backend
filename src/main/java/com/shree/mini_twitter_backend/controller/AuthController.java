package com.shree.mini_twitter_backend.controller;

import com.shree.mini_twitter_backend.dto.LoginRequest;
import com.shree.mini_twitter_backend.dto.LoginResponce;
import com.shree.mini_twitter_backend.dto.RegisterUserRequest;
import com.shree.mini_twitter_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponce> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserRequest> register(@RequestBody RegisterUserRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }

}
