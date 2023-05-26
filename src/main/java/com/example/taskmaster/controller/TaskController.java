package com.example.taskmaster.controller;

import com.example.taskmaster.service.TaskService;
import com.example.taskmaster.entity.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

//    @PostMapping("/create")
//    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, Authentication authentication) {
//        return taskService.createTask(task);
//    }
}
