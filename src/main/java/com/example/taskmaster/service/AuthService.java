package com.example.taskmaster.service;

import com.example.taskmaster.dto.UserDTO;
import com.example.taskmaster.entity.TaskmasterUser;
import com.example.taskmaster.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<TaskmasterUser> register(UserDTO userDTO) {
        Optional<TaskmasterUser> existingUser = userRepository.findTaskmasterUserByEmail(userDTO.getEmail());

        if (existingUser.isPresent()) {
            return Optional.empty();
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        TaskmasterUser newUser = TaskmasterUser.taskmasterUserFactory(userDTO);
        userRepository.save(newUser);
        return Optional.of(newUser);
    }
}
