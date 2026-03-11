package com.aceleradev.studyrats.controller;

import com.aceleradev.studyrats.domain.entity.User;
import com.aceleradev.studyrats.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public User me(Authentication authentication){
        String userId = authentication.getName();

        return userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
