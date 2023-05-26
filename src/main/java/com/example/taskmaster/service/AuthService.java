package com.example.taskmaster.service;

import com.example.taskmaster.dto.AuthRequest;
import com.example.taskmaster.dto.UserDTO;
import com.example.taskmaster.entity.TaskmasterUser;
import com.example.taskmaster.repository.UserRepository;
import com.example.taskmaster.security.JWTUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;

    public ResponseEntity<?> register(UserDTO userDTO) {
        TaskmasterUser existingUser = userRepository.findTaskmasterUserByEmail(userDTO.getEmail());

        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("error", "User already exists"));
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(TaskmasterUser.taskmasterUserFactory(userDTO));
        return ResponseEntity.ok("Successfully registered " + userDTO.getEmail());

    }

    public ResponseEntity<?> authenticateUser(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        TaskmasterUser user = (TaskmasterUser) userDetailsService.loadUserByUsername(request.getEmail());
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid username or password"));
        }
        String jwtToken = jwtUtils.generateToken(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User logged in successfully");
        response.put("token", jwtToken);

        return ResponseEntity.ok(response);
    }

    private int getIdFromAuthentication(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findTaskmasterUserByEmail(userDetails.getUsername()).getId();
    }
}
