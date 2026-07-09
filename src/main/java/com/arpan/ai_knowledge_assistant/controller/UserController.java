package com.arpan.ai_knowledge_assistant.controller;

import com.arpan.ai_knowledge_assistant.dto.AuthResponse;
import com.arpan.ai_knowledge_assistant.dto.LoginRequest;
import com.arpan.ai_knowledge_assistant.dto.RegisterRequest;
import com.arpan.ai_knowledge_assistant.dto.UserResponse;
import com.arpan.ai_knowledge_assistant.entity.User;
import com.arpan.ai_knowledge_assistant.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request){
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication) {
        return "Welcome " + authentication.getName();
    }

}
