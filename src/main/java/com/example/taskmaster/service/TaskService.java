package com.example.taskmaster.service;

import com.example.taskmaster.entity.Task;
import com.example.taskmaster.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final AuthService authService;

    public TaskService(TaskRepository taskRepository, AuthService authService) {
        this.taskRepository = taskRepository;
        this.authService = authService;
    }

//    public ResponseEntity<Task> createTaskById(Task task, Authentication authentication) {
//        if (task.)
//    }
}
