package com.thousand31.taskflow.service;

import com.thousand31.taskflow.config.JwtUtil;
import com.thousand31.taskflow.dto.auth.JwtResponse;
import com.thousand31.taskflow.dto.auth.LoginRequest;
import com.thousand31.taskflow.dto.auth.SignupRequest;
import com.thousand31.taskflow.exception.BadRequestException;
import com.thousand31.taskflow.model.Role;
import com.thousand31.taskflow.model.User;
import com.thousand31.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtResponse register(SignupRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email is already in use");
        }
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.ROLE_USER))
                .build();
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return buildJwtResponse(user, token);
    }

    public JwtResponse login(LoginRequest request){
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        authenticationManager.authenticate(authentication);
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new BadRequestException("Invalid email or password"));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        return buildJwtResponse(user, token);
    }

    private JwtResponse buildJwtResponse(User user, String token){
        return JwtResponse.builder()
                .accessToken(token)
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roles(
                        user.getRoles().stream()
                                .map(Enum::name)
                                .collect(Collectors.toSet())
                )
                .build();
    }

}
