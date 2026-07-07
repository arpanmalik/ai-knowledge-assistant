package com.arpan.ai_knowledge_assistant.service;

import com.arpan.ai_knowledge_assistant.dto.RegisterRequest;
import com.arpan.ai_knowledge_assistant.dto.UserResponse;
import com.arpan.ai_knowledge_assistant.entity.User;
import com.arpan.ai_knowledge_assistant.exception.EmailAlreadyExistsException;
import com.arpan.ai_knowledge_assistant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}
