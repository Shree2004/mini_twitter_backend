package java.controller;

import java.dto.LoginRequest;
import java.dto.LoginResponce;
import java.dto.RegisterUserRequest;
import java.entity.User;
import java.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponce> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}