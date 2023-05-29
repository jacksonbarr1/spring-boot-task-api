package com.example.taskapi.controller;

import com.example.taskapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public ResponseEntity<?> index() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/create")
//    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task, Authentication authentication) {
//        return taskService.createTask(task);
//    }
}
