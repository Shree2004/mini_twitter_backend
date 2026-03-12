package com.shree.mini_twitter_backend.service;

import com.shree.mini_twitter_backend.dto.LoginRequest;
import com.shree.mini_twitter_backend.dto.LoginResponce;
import com.shree.mini_twitter_backend.dto.RegisterUserRequest;
import com.shree.mini_twitter_backend.entity.User;
import com.shree.mini_twitter_backend.enums.AccountStatus;
import com.shree.mini_twitter_backend.enums.role;
import com.shree.mini_twitter_backend.repository.UserRepository;
import com.shree.mini_twitter_backend.security.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private UserRepository userRepository;

    public LoginResponce login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = authUtil.getAccessToken(user);

        return new LoginResponce(token, user.getUserId());

    }

    public RegisterUserRequest register(RegisterUserRequest registerRequest) {

        User user = userRepository.findByUsername(registerRequest.getUsername())
                .orElse(null);

        if (user != null) throw new IllegalArgumentException("User already there");

        user = userRepository.save(User.builder()
                .username(registerRequest.getUsername())
                .mobileNumber(registerRequest.getMobileNumber())
                .email(registerRequest.getEmail())
                .bio(registerRequest.getBio())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role.valueOf("USER"))
                .accountStatus(AccountStatus.valueOf("ACTIVE"))
                .build());

        return  new RegisterUserRequest(user);
    }
}
