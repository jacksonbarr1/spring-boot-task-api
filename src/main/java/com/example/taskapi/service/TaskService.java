package com.example.taskapi.service;

import com.example.taskapi.repository.TaskRepository;
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
