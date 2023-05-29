package com.example.taskapi.service;

import com.example.taskapi.dto.AuthRequest;
import com.example.taskapi.dto.UserDTO;
import com.example.taskapi.entity.UserEntity;
import com.example.taskapi.repository.UserRepository;
import com.example.taskapi.security.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserDetailsService userDetailsService;

    @Mock
    JWTUtils jwtUtils;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerUser_Success() {
        UserDTO userDTO = new UserDTO("test@test.com", "password123", "Test", "User");

        when(userRepository.findUserEntityByEmail(userDTO.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");

        ResponseEntity<?> response = authService.register(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully registered " + userDTO.getEmail(), response.getBody());

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void registerUser_Failure_UserExists() {
        UserDTO userDTO = new UserDTO("test@test.com", "password123", "Test", "User");
        UserEntity existingUser = new UserEntity();

        when(userRepository.findUserEntityByEmail(userDTO.getEmail())).thenReturn(existingUser);

        ResponseEntity<?> response = authService.register(userDTO);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(Collections.singletonMap("error", "User already exists"), response.getBody());

        verify(userRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    public void authenticateUser_SuccessfulAuthentication() {
        AuthRequest request = new AuthRequest("test@example.com", "password123");
        UserEntity user = new UserEntity();

        when(userDetailsService.loadUserByUsername(request.getEmail())).thenReturn(user);
        when(jwtUtils.generateToken(user)).thenReturn("fakeToken");

        ResponseEntity<?> response = authService.authenticateUser(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        var body = (HashMap<String, Object>) response.getBody();
        assertEquals("User logged in successfully", body.get("message"));
        assertEquals("fakeToken", body.get("token"));
    }

    @Test
    public void authenticateUser_FailedAuthentication() {
        AuthRequest request = new AuthRequest("test@example.com", "password123");

        when(userDetailsService.loadUserByUsername(request.getEmail())).thenReturn(null);

        ResponseEntity<?> response = authService.authenticateUser(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        var body = (Map<String, String>) response.getBody();
        assertEquals("Invalid username or password", body.get("error"));
    }
}
