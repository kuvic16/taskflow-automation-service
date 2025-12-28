package com.thousand31.taskflow.controller;

import com.thousand31.taskflow.dto.auth.JwtResponse;
import com.thousand31.taskflow.dto.auth.LoginRequest;
import com.thousand31.taskflow.dto.auth.SignupRequest;
import com.thousand31.taskflow.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody SignupRequest request){
        JwtResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        JwtResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
