package java.service;

import java.dto.LoginRequest;
import java.dto.LoginResponce;
import java.dto.RegisterUserRequest;
import java.entity.User;
import java.enums.AccountStatus;
import java.enums.role;
import java.repository.UserRepository;
import java.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User register(RegisterUserRequest request){

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .bio(request.getBio())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role.USER)
                .accountStatus(AccountStatus.ACTIVE)
                .build();

        return userRepository.save(user);
    }

    public LoginResponce login(LoginRequest request){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        String token = jwtUtil.generateToken(user);

        return new LoginResponce(token, user.getUserId());
    }
}