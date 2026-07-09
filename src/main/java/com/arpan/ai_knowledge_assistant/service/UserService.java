package com.arpan.ai_knowledge_assistant.service;

import com.arpan.ai_knowledge_assistant.dto.AuthResponse;
import com.arpan.ai_knowledge_assistant.dto.LoginRequest;
import com.arpan.ai_knowledge_assistant.dto.RegisterRequest;
import com.arpan.ai_knowledge_assistant.dto.UserResponse;
import com.arpan.ai_knowledge_assistant.entity.User;
import com.arpan.ai_knowledge_assistant.exception.EmailAlreadyExistsException;
import com.arpan.ai_knowledge_assistant.repository.UserRepository;
import com.arpan.ai_knowledge_assistant.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomerUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public UserResponse register(RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))// We'll hash this later with BCrypt
                .role("USER")
                .build();

        User saved = userRepository.save(user);
        UserResponse response = UserResponse.builder()
                .id(saved.getId())
                .username(saved.getFullName())
                .email(saved.getEmail())
                .password(saved.getPassword())
                .build();

        return response;
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getEmail());

        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
