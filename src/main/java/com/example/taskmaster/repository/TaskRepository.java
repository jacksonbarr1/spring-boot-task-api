package com.example.taskmaster.repository;

import com.example.taskmaster.entity.Task;
import com.example.taskmaster.entity.TaskmasterUser;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
