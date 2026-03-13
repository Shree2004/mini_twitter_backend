package com.shree.mini_twitter_backend.service;

import com.shree.mini_twitter_backend.dto.*;
import com.shree.mini_twitter_backend.entity.User;
import com.shree.mini_twitter_backend.enums.AccountStatus;
import com.shree.mini_twitter_backend.enums.Role;
import com.shree.mini_twitter_backend.repository.UserRepository;
import com.shree.mini_twitter_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request){

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBio(request.getBio());
        user.setMobileNumber(request.getMobileNumber());
        user.setRole(Role.USER);
        user.setAccountStatus(AccountStatus.valueOf("ACTIVE"));

        userRepository.save(user);

        return "User registered successfully";
    }


    public AuthResponse login(LoginRequest request){

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getAccountStatus() == AccountStatus.BANNED){
            throw new RuntimeException("User is banned by admin");
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getUsername());

        return new AuthResponse(token);
    }

}