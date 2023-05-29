package com.example.taskapi.service;

import com.example.taskapi.dto.AuthRequest;
import com.example.taskapi.dto.UserDTO;
import com.example.taskapi.entity.UserEntity;
import com.example.taskapi.repository.UserRepository;
import com.example.taskapi.security.JWTUtils;
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
        UserEntity existingUser = userRepository.findUserEntityByEmail(userDTO.getEmail());

        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("error", "User already exists"));
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(UserEntity.userFactory(userDTO));
        return ResponseEntity.ok("Successfully registered " + userDTO.getEmail());

    }

    public ResponseEntity<?> authenticateUser(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserEntity user = (UserEntity) userDetailsService.loadUserByUsername(request.getEmail());
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
        return userRepository.findUserEntityByEmail(userDetails.getUsername()).getId();
    }
}
