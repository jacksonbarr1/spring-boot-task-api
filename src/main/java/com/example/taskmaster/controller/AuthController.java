package com.example.taskmaster.controller;


import com.example.taskmaster.dto.AuthRequest;
import com.example.taskmaster.dto.UserDTO;
import com.example.taskmaster.entity.TaskmasterUser;
import com.example.taskmaster.service.AuthService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        if (authService.register(userDTO).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", "Username already exists"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        // TODO align with authenticateUser
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest request) {
        return authService.authenticateUser(request);
    }
}
