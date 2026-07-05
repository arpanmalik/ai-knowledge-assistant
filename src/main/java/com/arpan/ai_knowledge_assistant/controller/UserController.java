package com.arpan.ai_knowledge_assistant.controller;

import com.arpan.ai_knowledge_assistant.dto.RegisterRequest;
import com.arpan.ai_knowledge_assistant.dto.UserResponse;
import com.arpan.ai_knowledge_assistant.entity.User;
import com.arpan.ai_knowledge_assistant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request){
        return userService.register(request);
    }

}
